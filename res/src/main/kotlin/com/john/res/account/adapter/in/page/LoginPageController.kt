package com.john.res.account.adapter.`in`.page

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView

/**
 * @author yoonho
 * @since 2023.03.1
 */
@Controller
class LoginPageController {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 로그인 페이지
     *
     * @return [String]
     * @author yoonho
     * @since 2023.05.10
     */
    @GetMapping(value = ["/login"])
    fun loginPage(redirectUri: String?): ModelAndView {
        log.info(" >>> [loginPage] redirectUri: $redirectUri")

        val mv = ModelAndView("login/login")
        mv.addObject("redirectUri", redirectUri)

        return mv
    }


    /**
     * 회원가입 페이지
     *
     * @return [String]
     * @author yoonho
     * @since 2023.05.10
     */
    @GetMapping("/login/register")
    fun registerPage(): String =
        "login/register"

    /**
     * index 페이지
     *
     * @return [String]
     * @author yoonho
     * @since 2023.05.10
     */
    @GetMapping(value = ["/", "/home"])
    @ResponseBody
    fun home(): String =
        "home"
}