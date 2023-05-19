package com.john.auth.authorization.application

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeInput
import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeRedirectInput
import com.john.auth.authorization.application.dto.AuthorizationCodeDto
import com.john.auth.authorization.application.port.`in`.AuthorizationCodeCheckUseCase
import com.john.auth.authorization.application.port.`in`.AuthorizationCodeRegistUseCase
import com.john.auth.authorization.application.port.out.AuthorizationCodeFindPort
import com.john.auth.authorization.application.port.out.AuthorizationCodeSavePort
import com.john.auth.common.utils.Base64StringKeyGenerator
import com.john.auth.common.utils.EnvironmentUtils
import com.john.auth.common.utils.ParseUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.05.13
 */
@Service
class AuthorizationService(
    private val authorizationCodeFindPort: AuthorizationCodeFindPort,
    private val authorizationCodeSavePort: AuthorizationCodeSavePort
): AuthorizationCodeCheckUseCase, AuthorizationCodeRegistUseCase {
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
        authorizationCodeFindPort.checkClient(input = input)

        val consentUri = EnvironmentUtils.getProperty("auth.url.consent")
        val loginPageUri = EnvironmentUtils.getProperty("auth.url.login")
        val state = ParseUtils.getState(redirectUrl = input.redirect_uri, scope = input.scope, state = input.state!!, clientId = input.client_id)

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
        authorizationCodeSavePort.register(input = authorizationCodeInfo)

        return authorizationCode
    }
}