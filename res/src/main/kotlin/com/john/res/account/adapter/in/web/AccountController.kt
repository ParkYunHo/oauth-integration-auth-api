package com.john.res.account.adapter.`in`.web

import com.john.res.account.adapter.`in`.web.dto.AuthInput
import com.john.res.account.adapter.`in`.web.dto.RegisterInput
import com.john.res.account.application.port.`in`.LoginUseCase
import com.john.res.account.application.port.`in`.RegisterUseCase
import com.john.res.common.BaseResponse
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author yoonho
 * @since 2023.05.09
 */
@RestController
class AccountController(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) {

    @GetMapping("/api/login/auth")
    fun auth(@Validated input: AuthInput): BaseResponse<Boolean> {
        val result = loginUseCase.login(input)
        return BaseResponse.Success(result)
    }

    @GetMapping("/api/login/register")
    fun register(@Validated input: RegisterInput) {

    }
}