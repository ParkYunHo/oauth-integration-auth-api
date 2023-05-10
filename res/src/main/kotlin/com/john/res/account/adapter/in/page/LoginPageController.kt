package com.john.res.account.adapter.`in`.page

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 * @author yoonho
 * @since 2023.03.1
 */
@Controller
class LoginPageController {

    /**
     * 로그인 페이지
     *
     * @return [String]
     * @author yoonho
     * @since 2023.05.10
     */
    @GetMapping(value = ["/login"])
    fun loginPage(): String =
        "login/login"

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
    @GetMapping("/")
    @ResponseBody
    fun home(): String =
        "home"
}