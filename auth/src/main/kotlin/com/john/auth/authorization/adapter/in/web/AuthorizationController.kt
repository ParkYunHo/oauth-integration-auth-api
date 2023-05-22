package com.john.auth.authorization.adapter.`in`.web

import com.john.auth.authorization.adapter.`in`.web.dto.AccessTokenInput
import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeInput
import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeRedirectInput
import com.john.auth.authorization.application.dto.TokenInfo
import com.john.auth.authorization.application.port.`in`.AccessTokenRegistUseCase
import com.john.auth.authorization.application.port.`in`.AuthorizationCodeCheckUseCase
import com.john.auth.authorization.application.port.`in`.AuthorizationCodeRegistUseCase
import com.john.auth.common.BaseResponse
import com.john.auth.consent.application.port.`in`.ConsentCheckUseCase
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author yoonho
 * @since 2023.05.13
 */
@RestController
class AuthorizationController(
    private val authorizationCodeCheckUseCase: AuthorizationCodeCheckUseCase,
    private val authorizationCodeRegistUseCase: AuthorizationCodeRegistUseCase,
    private val accessTokenRegistUseCase: AccessTokenRegistUseCase
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

    /**
     * 인가코드 발급
     *
     * @param input [AuthorizationCodeRedirectInput]
     * @param response [HttpServletResponse]
     * @author yoonho
     * @since 2023.05.15
     */
    @GetMapping("/oauth/authorize/code")
    fun authorizeCode(@Validated input: AuthorizationCodeRedirectInput, response: HttpServletResponse) {
        log.info(" >>> [authorizeCode] input: $input")

        val authorizationCode = authorizationCodeRegistUseCase.register(input)
        response.sendRedirect("${input.redirect_uri}?code=$authorizationCode")
    }

    /**
     * 토큰 발급
     *
     * @param input [AccessTokenInput]
     * @return [BaseResponse]<[TokenInfo]>
     * @author yoonho
     * @since 2023.05.22
     */
    @PostMapping("/oauth/token")
    fun token(@Validated input: AccessTokenInput): BaseResponse<TokenInfo> {
        log.info(" >>> [token] input: $input")

        val result = accessTokenRegistUseCase.register(input = input)
        return BaseResponse.Success(data = result)
    }
}