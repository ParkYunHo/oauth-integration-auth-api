package com.john.res.account.adapter.`in`.web

import com.john.res.account.adapter.`in`.web.dto.AuthInput
import com.john.res.account.adapter.`in`.web.dto.RegisterInput
import com.john.res.account.application.port.`in`.LoginUseCase
import com.john.res.account.application.port.`in`.RegisterUseCase
import com.john.res.common.BaseResponse
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * @author yoonho
 * @since 2023.05.09
 */
@RestController
class AccountController(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 로그인 프로세스
     *
     * @param input [AuthInput]
     * @return [BaseResponse]<[Boolean]>
     * @author yoonho
     * @since 2023.05.10
     */
    @PostMapping("/api/login/auth")
    fun auth(@RequestBody @Validated input: AuthInput): BaseResponse<Boolean> {
        log.info(" >>> [auth] input: $input")

        val result = loginUseCase.login(input)
        return BaseResponse.Success(result)
    }

    /**
     * 회원가입 프로세스
     *
     * @param input [RegisterInput]
     * @return [BaseResponse]<[Boolean]>
     * @author yoonho
     * @since 2023.05.10
     */
    @PostMapping("/api/login/register")
    fun register(@RequestBody @Validated input: RegisterInput): BaseResponse<Boolean> {
        log.info(" >>> [register] input: $input")

        val result = registerUseCase.register(input)
        return BaseResponse.Success(result)
    }
}