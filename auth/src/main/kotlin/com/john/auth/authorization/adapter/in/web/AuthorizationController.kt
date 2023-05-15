package com.john.auth.authorization.adapter.`in`.web

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeInput
import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeRedirectInput
import com.john.auth.authorization.application.port.`in`.AuthorizationCodeCheckUseCase
import com.john.auth.common.utils.ParseUtils
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author yoonho
 * @since 2023.05.13
 */
@RestController
class AuthorizationController(
    private val authorizationCodeCheckUseCase: AuthorizationCodeCheckUseCase
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 인가코드 발급
     *
     * @param input [AuthorizationCodeInput]
     * @param response [HttpServletResponse]
     * @author yoonho
     * @since 2023.05.15
     */
    @GetMapping("/oauth/authorize")
    fun authorize(@Validated input: AuthorizationCodeInput, response: HttpServletResponse) {
        log.info(" >>> [authorize] input: $input")

        val redirectUri = authorizationCodeCheckUseCase.check(input = input)
        response.sendRedirect(redirectUri)
    }

    @GetMapping("/oauth/authorize/code")
    fun authorizeCode(@Validated input: AuthorizationCodeRedirectInput, response: HttpServletResponse) {
        log.info(" >>> [authorizeCode] input: $input")

        // TODO: code&error_description체크, Consent 체크, 인가코드 발급(to: redirect_uri)
    }

}