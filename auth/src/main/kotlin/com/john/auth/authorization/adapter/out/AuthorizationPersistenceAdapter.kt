package com.john.auth.authorization.adapter.out

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeInput
import com.john.auth.authorization.application.dto.AuthorizationCodeDto
import com.john.auth.authorization.application.dto.IssueTokenDto
import com.john.auth.authorization.application.dto.TokenInfo
import com.john.auth.authorization.application.port.out.FindPort
import com.john.auth.authorization.application.port.out.SavePort
import com.john.auth.authorization.domain.Authorization
import com.john.auth.client.adapter.out.RegisteredClientRepository
import com.john.auth.client.domain.RegisteredClient
import com.john.auth.common.exception.*
import org.springframework.stereotype.Repository

/**
 * @author yoonho
 * @since 2023.05.13
 */
@Repository
class AuthorizationPersistenceAdapter(
    private val authorizationRepository: AuthorizationRepository,
    private val clientRepository: RegisteredClientRepository
): FindPort, SavePort {

    /**
     * Client 인증
     *
     * @param clientId [String]
     * @param clientSecret [String]
     * @return [String]
     * @author yoonho
     * @since 2023.05.14
     */
    override fun checkClient(clientId: String, clientSecret: String): RegisteredClient =
        clientRepository.findByRestClientIdAndClientSecret(
            restClientId = clientId,
            clientSecret = clientSecret
        )
            .orElseThrow { throw InvalidClientException() }

    /**
     * 인가코드 저장
     *
     * @param input [AuthorizationCodeDto]
     * @author yoonho
     * @since 2023.05.19
     */
    override fun authorizationCodeRegister(input: AuthorizationCodeDto) {
        authorizationRepository.save(
            Authorization(
                registeredClientId = input.clientId,
                principalName = input.userId,
                authorizationGrantType = input.grantType,
                authorizedScopes = input.scopes,
                state = input.state,
                authorizationCodeValue = input.authorizationCode,
                authorizationCodeIssuedAt = input.authorizationCodeIssuedAt,
                authorizationCodeExpiresAt = input.authorizationCodeExpiresAt
            )
        )
    }

    /**
     * 인가코드 체크
     *
     * @param clientId [String]
     * @param authorizationCode [String]
     * @return [Authorization]
     */
    override fun checkAuthorizationCode(clientId: String, authorizationCode: String): Authorization =
        authorizationRepository.findByRegisteredClientIdAndAuthorizationCodeValue(registeredClientId = clientId, authorizationCodeValue = authorizationCode)
            .orElseThrow { throw InvalidTokenException() }

    /**
     * 토큰발급
     *
     * @param authorization [Authorization]
     * @author yoonho
     * @since 2023.05.23
     */
    override fun tokenInfoRegister(authorization: Authorization) {
        authorizationRepository.save(authorization)
    }

    /**
     * RefreshToken 체크
     *
     * @param clientId [String]
     * @param refreshToken [String]
     * @return [Authorization]
     */
    override fun checkRefreshToken(clientId: String, refreshToken: String): Authorization =
        authorizationRepository.findByRegisteredClientIdAndRefreshTokenValue(registeredClientId = clientId, refreshTokenValue = refreshToken)
            .orElseThrow { throw InvalidTokenException() }

    /**
     * AccessToken 체크
     *
     * @param accessToken [String]
     * @return [Authorization]
     * @author yoonho
     * @since 2023.05.24
     */
    override fun checkAccessToken(accessToken: String): Authorization =
        authorizationRepository.findByAccessTokenValue(accessTokenValue = accessToken)
            .orElseThrow { throw InvalidTokenException() }
}