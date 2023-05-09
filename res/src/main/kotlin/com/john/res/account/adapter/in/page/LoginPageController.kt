package com.john.res.account.adapter.`in`.page

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

/**
 * @author yoonho
 * @since 2023.03.1
 */
@Controller
class LoginPageController {

    @GetMapping(value = ["/login"])
    fun loginPage(): String =
        "login/login"

    @GetMapping("/login/register")
    fun registerPage(): String =
        "login/register"

    @GetMapping("/")
    @ResponseBody
    fun home(): String =
        "home"
}