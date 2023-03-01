package com.john.res.account.adapter.`in`.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author yoonho
 * @since 2023.03.1
 */
@RestController
class AccountController {

    @GetMapping(value = ["/", "/login"])
    fun login(): String =
        "test"
}