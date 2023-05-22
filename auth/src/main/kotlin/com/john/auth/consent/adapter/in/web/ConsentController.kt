package com.john.auth.consent.adapter.`in`.web

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeRedirectInput
import com.john.auth.client.application.port.`in`.FindScopesUseCase
import com.john.auth.common.context.AuthorizationContext
import com.john.auth.common.context.AuthorizationToken
import com.john.auth.common.exception.NotRegisteredUserException
import com.john.auth.common.utils.ParseUtils
import com.john.auth.consent.application.port.`in`.ConsentCheckUseCase
import com.john.auth.consent.application.port.`in`.ConsentSaveUseCase
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

/**
 * @author yoonho
 * @since 2023.05.19
 */
@RestController
class ConsentController(
    private val consentCheckUseCase: ConsentCheckUseCase,
    private val findScopesUseCase: FindScopesUseCase,
    private val consentSaveUseCase: ConsentSaveUseCase
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 정보동의여부 체크
     *
     * @param input [AuthorizationCodeRedirectInput]
     * @param response [HttpServletResponse]
     * @author yoonho
     * @since 2023.05.19
     */
    @GetMapping("/oauth/consent")
    fun consentCheck(@Validated input: AuthorizationCodeRedirectInput, response: HttpServletResponse) {
        log.info(" >>> [consentCheck] input: $input")

        if(input.code.isNullOrEmpty() && input.error_description.isNullOrEmpty()) {
            val isRegisteredConsent = consentCheckUseCase.checkConsent(input)
            val param = ParseUtils.getAuthorizationParameter(input)
            if(isRegisteredConsent) {
                // Consent 존재시 인가코드 발급
                response.sendRedirect("http://localhost:9000/oauth/authorize/code?$param")
            }else {
                // Consent 미존재시 등록화면 이동
                response.sendRedirect("http://localhost:9000/oauth/consent/page?$param")
            }
        }else {
            throw NotRegisteredUserException()
        }
    }

    /**
     * 정보동의 HTML화면
     *
     * @param input [AuthorizationCodeRedirectInput]
     * @return [ModelAndView]
     * @author yoonho
     * @since 2023.05.19
     */
    @GetMapping("/oauth/consent/page")
    fun consentPage(input: AuthorizationCodeRedirectInput): ModelAndView {
        log.info(" >>> [consentPage] input: $input")

        val mv = ModelAndView("consent")
        mv.addObject("clientId", input.client_id)
        mv.addObject("clientSecret", input.client_secret)
        mv.addObject("redirectUri", input.redirect_uri)
        mv.addObject("userId", input.userId)
        mv.addObject("state", input.state)

        val scopes = findScopesUseCase.findScopes(clientId = input.client_id, clientSecret = input.client_secret)
        mv.addObject("scopes", scopes.split(",").toList())

        return mv
    }

    /**
     * 정보동의 등록
     *
     * @param input [AuthorizationCodeRedirectInput]
     * @param response [HttpServletResponse]
     * @author yoonho
     * @since 2023.05.19
     */
    @PostMapping("/oauth/consent/register")
    fun register(@ModelAttribute input: AuthorizationCodeRedirectInput, response: HttpServletResponse) {
        log.info(" >>> [register] input: $input")

        // Consent 저장
        consentSaveUseCase.saveConsent(input)

        // 인가코드 발급
        val param = ParseUtils.getAuthorizationParameter(input)
        response.sendRedirect("http://localhost:9000/oauth/authorize/code?$param")
    }
}