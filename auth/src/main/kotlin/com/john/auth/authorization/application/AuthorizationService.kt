package com.john.auth.authorization.application

import com.john.auth.authorization.adapter.`in`.web.dto.AccessTokenInput
import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeInput
import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeRedirectInput
import com.john.auth.authorization.application.dto.AuthorizationCodeDto
import com.john.auth.authorization.application.dto.IntrospectDto
import com.john.auth.authorization.application.dto.LogoutDto
import com.john.auth.authorization.application.dto.TokenInfo
import com.john.auth.authorization.application.port.`in`.AccessTokenIntrospectUseCase
import com.john.auth.authorization.application.port.`in`.AccessTokenRegistUseCase
import com.john.auth.authorization.application.port.`in`.AuthorizationCodeCheckUseCase
import com.john.auth.authorization.application.port.`in`.AuthorizationCodeRegistUseCase
import com.john.auth.authorization.application.port.out.FindPort
import com.john.auth.authorization.application.port.out.SavePort
import com.john.auth.client.application.port.`in`.FindAppUserIdUseCase
import com.john.auth.authorization.application.port.`in`.LogoutUseCase
import com.john.auth.client.application.port.`in`.RegistAppUserIdUseCase
import com.john.auth.common.exception.*
import com.john.auth.common.utils.Base64StringKeyGenerator
import com.john.auth.common.utils.EnvironmentUtils
import com.john.auth.common.utils.ParseUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.*
import java.time.temporal.ChronoUnit

/**
 * @author yoonho
 * @since 2023.05.13
 */
@Service
class AuthorizationService(
    private val findPort: FindPort,
    private val savePort: SavePort,
    private val registAppUserIdUseCase: RegistAppUserIdUseCase,
    private val findAppUserIdUseCase: FindAppUserIdUseCase
):  AuthorizationCodeCheckUseCase,
    AuthorizationCodeRegistUseCase,
    AccessTokenRegistUseCase,
    AccessTokenIntrospectUseCase,
    LogoutUseCase
{
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 인가코드 요청 Input 검사
     *
     * @param input [AuthorizationCodeInput]
     * @return [String]
     * @author yoonho
     * @since 2023.05.15
     */
    override fun check(input: AuthorizationCodeInput): String {
        val registeredClient = findPort.checkClient(clientId = input.client_id, clientSecret = input.client_secret)

        // grant_type 체크
        if(!registeredClient.authorizationGrantTypes.contains(input.response_type)) {
            throw UnsupportedResponseTypeException()
        }
        // scope 체크
        if(!registeredClient.scopes.contains(input.scope)) {
            throw InvalidScopeException()
        }
        // redirect_uri 체크
        val registeredClientRedirectUris = registeredClient.redirectUris?.split(",") ?: listOf()
        if(!registeredClientRedirectUris.contains(input.redirect_uri)) {
            throw RedirectMismatchException()
        }

        val consentUri = EnvironmentUtils.getProperty("auth.url.consent")
        val loginPageUri = EnvironmentUtils.getProperty("auth.url.login")
        val state = ParseUtils.getState(input = input)

        return "$loginPageUri?redirectUri=$consentUri&state=$state"
    }

    /**
     * 인가코드 발급
     *
     * @param input [AuthorizationCodeRedirectInput]
     * @return [String]
     * @author yoonho
     * @since 2023.05.19
     */
    override fun register(input: AuthorizationCodeRedirectInput): String {
        // 인가코드 발급
        val authorizationCode = Base64StringKeyGenerator.generateKey(keyLength = 96)

        val authorizationCodeInfo = AuthorizationCodeDto(
            clientId = input.client_id,
            userId = input.userId!!,
            grantType = "authorization_code",
            scopes = input.scope,
            state = input.state ?: "",
            authorizationCode = authorizationCode,
            authorizationCodeIssuedAt = LocalDateTime.now(),
            authorizationCodeExpiresAt = LocalDateTime.now().plusMinutes(10L)
        )
        // 인가코드 저장
        savePort.authorizationCodeRegister(input = authorizationCodeInfo)
        // AppUserId 발급
        registAppUserIdUseCase.registAppUserId(clientId = input.client_id, userId = input.userId)

        return authorizationCode
    }

    /**
     * 엑세스토큰 발급
     *
     * @param input [AccessTokenInput]
     * @return [String]
     * @author yoonho
     * @since 2023.05.19
     */
    override fun register(input: AccessTokenInput): TokenInfo {
        // Client 인증로직
        findPort.checkClient(clientId = input.client_id, clientSecret = input.client_secret)

        when(input.grant_type) {
            "authorization_code" -> {
                if(input.code.isNullOrEmpty()) {
                    throw BadRequestException()
                }

                val authorization = findPort.checkAuthorizationCode(clientId = input.client_id, authorizationCode = input.code)

                // 만료여부 체크
                if(LocalDateTime.now().isAfter(authorization.authorizationCodeExpiresAt!!)) {
                    throw InvalidTokenException()
                }

                val accessToken = Base64StringKeyGenerator.generateKey(keyLength = 96)
                val expiresIn = 60 * 60 * 6     // 6시간
                val refreshToken = Base64StringKeyGenerator.generateKey(keyLength = 96)
                val refreshTokenExpiresIn = 60 * 60 * 24 * 60       // 2달(60일)

                authorization.accessTokenValue = accessToken
                authorization.accessTokenIssuedAt = LocalDateTime.now()
                authorization.accessTokenExpiresAt = LocalDateTime.now().plusHours(6L)
                authorization.accessTokenType = "bearer"
                authorization.accessTokenScopes = ""        // TODO: 추후 어떻게 할지 결정

                authorization.refreshTokenValue = refreshToken
                authorization.refreshTokenIssuedAt = LocalDateTime.now()
                authorization.refreshTokenExpiresAt = LocalDateTime.now().plusMonths(2L)

                // 토큰정보 저장
                savePort.tokenInfoRegister(authorization = authorization)

                return TokenInfo(
                    token_type = authorization.accessTokenType!!,
                    access_token = authorization.accessTokenValue!!,
                    expires_in = expiresIn.toLong(),
                    refresh_token = authorization.refreshTokenValue!!,
                    refresh_token_expires_in = refreshTokenExpiresIn.toLong()
                )
            }
            "refresh_token" -> {
                if(input.refresh_token.isNullOrEmpty()) {
                    throw BadRequestException()
                }

                val authorization = findPort.checkRefreshToken(clientId = input.client_id, refreshToken = input.refresh_token)

                // 만료여부 체크
                if(LocalDateTime.now().isAfter(authorization.refreshTokenExpiresAt!!)) {
                    throw InvalidTokenException()
                }

                val accessToken = Base64StringKeyGenerator.generateKey(keyLength = 96)
                val expiresIn = 60 * 60 * 6     // 6시간
                var refreshToken = ""
                var refreshTokenExpiresIn = 0

                authorization.accessTokenValue = accessToken
                authorization.accessTokenIssuedAt = LocalDateTime.now()
                authorization.accessTokenExpiresAt = LocalDateTime.now().plusHours(6L)
                authorization.accessTokenType = "bearer"
                authorization.accessTokenScopes = ""        // TODO: 추후 어떻게 할지 결정

                // refreshToken 만료기간이 1달 이내로 남았을 경우, refreshToken 갱신
                val diff = Period.between(LocalDate.now(), authorization.refreshTokenExpiresAt!!.toLocalDate())
                if(diff.months < 1) {
                    refreshToken = Base64StringKeyGenerator.generateKey(keyLength = 96)
                    refreshTokenExpiresIn = 60 * 60 * 24 * 60       // 2달(60일)

                    authorization.refreshTokenValue = refreshToken
                    authorization.refreshTokenIssuedAt = LocalDateTime.now()
                    authorization.refreshTokenExpiresAt = LocalDateTime.now().plusMonths(2L)
                }

                // 토큰정보 저장
                savePort.tokenInfoRegister(authorization = authorization)

                return TokenInfo(
                    token_type = authorization.accessTokenType!!,
                    access_token = authorization.accessTokenValue!!,
                    expires_in = expiresIn.toLong(),
                    refresh_token = refreshToken,
                    refresh_token_expires_in = refreshTokenExpiresIn.toLong()
                )
            }
            else -> throw BadRequestException("invalid grant_type")
        }
    }

    /**
     * 토큰검사
     *
     * @param accessToken [String]
     * @return [IntrospectDto]
     * @author yoonho
     * @since 2023.05.24
     */
    override fun introspect(accessToken: String): IntrospectDto {
        val authorization = findPort.checkAccessToken(accessToken = accessToken)
        val expiresIn = authorization.accessTokenExpiresAt!!

        // 만료여부 체크
        if(LocalDateTime.now().isAfter(expiresIn)) {
            throw InvalidTokenException()
        }

        // 남은 만료시간 계산
        val diff = ChronoUnit.SECONDS.between(LocalDateTime.now(), expiresIn)

        // AppUserId 조회
        val appUserId = findAppUserIdUseCase.findAppUserId(clientId = authorization.registeredClientId, userId = authorization.principalName)

        return IntrospectDto(
            appUserId = appUserId,
            expiresIn = diff,
            clientId = authorization.registeredClientId
        )
    }

    /**
     * 로그아웃
     *
     * @param userId [String]
     * @param accessToken [String]
     * @return [String]
     * @author yoonho
     * @since 2023.05.27
     */
    override fun logout(userId: String, accessToken: String): LogoutDto {
        // TODO:
        //      - Spring Cloud Feign 사용해서 Resource Server 로그아웃 API 호출
        //      - 성공응답시, AccessToken, RefreshToken 관련 ExpiredAt를 현재 날짜로 변경
        //      - 로그아웃 전체 성공시, target_id 응답



        savePort.logout(userId = userId, accessToken = accessToken)
        return LogoutDto(userId = userId)
    }
}