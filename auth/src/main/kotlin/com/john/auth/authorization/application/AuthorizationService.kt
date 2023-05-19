package com.john.auth.authorization.application

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeInput
import com.john.auth.authorization.application.port.`in`.AuthorizationCodeCheckUseCase
import com.john.auth.authorization.application.port.out.AuthorizationCodeFindPort
import com.john.auth.common.utils.EnvironmentUtils
import com.john.auth.common.utils.ParseUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * @author yoonho
 * @since 2023.05.13
 */
@Service
class AuthorizationService(
    private val authorizationCodeFindPort: AuthorizationCodeFindPort
): AuthorizationCodeCheckUseCase {
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

}