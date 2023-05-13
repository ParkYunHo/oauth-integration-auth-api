package com.john.auth.authorization.adapter.`in`.web

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeInput
import com.john.auth.authorization.application.port.`in`.AuthorizationCodeCheckUseCase
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

    @GetMapping("/oauth/authorize")
    fun authorize(@Validated input: AuthorizationCodeInput, response: HttpServletResponse) {
        log.info(" >>> [authorize] input: $input")

        val redirectUri = authorizationCodeCheckUseCase.check(input = input)
        response.sendRedirect("http://localhost:8080/login?redirectUri=$redirectUri")
    }
}