package com.john.res.account.adapter.`in`.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

/**
 * @author yoonho
 * @since 2023.03.1
 */
@RestController
class AccountController {

    @GetMapping(value = ["/", "/login"])
    fun login(): ModelAndView =
        ModelAndView("login/login")

    @GetMapping("/login/register")
    fun register(): ModelAndView =
        ModelAndView("login/register")
}