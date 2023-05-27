package com.john.res.account.adapter.`in`.web

import com.john.res.account.adapter.`in`.web.dto.AuthInput
import com.john.res.account.adapter.`in`.web.dto.RegisterInput
import com.john.res.account.application.port.`in`.LoginUseCase
import com.john.res.account.application.port.`in`.RegisterUseCase
import com.john.res.common.BaseResponse
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 로그인 프로세스
     *
     * @param input [AuthInput]
     * @return [BaseResponse]<[String]>
     * @author yoonho
     * @since 2023.05.10
     */
    @PostMapping("/api/login/auth")
    fun auth(@RequestBody @Validated input: AuthInput): BaseResponse<String> {
        log.info(" >>> [auth] input: $input")

        // 로그인 성공이후 리다이렉트 경로 설정
        val redirectUri =
            if(input.redirectUri.isNullOrEmpty()) {
                "/"
            } else {
                // 로그인 프로세스 (미등록 사용자일 경우 Exception 발생)
                loginUseCase.login(input)
            }
        return BaseResponse.Success(redirectUri)
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

    /**
     * 로그아웃 (인가서버 호출용)
     *
     * @param userId [String]
     * @return [BaseResponse]<[Boolean]>
     * @author yoonho
     * @since 2023.05.27
     */
    @PostMapping("/api/logout")
    fun logout(userId: String): BaseResponse<Boolean> {
        log.info(" >>> [logout] userId: $userId")

        // Resource Server에서 로그아웃처리를 해야하지만 별도 처리없이 true만 리턴

        return BaseResponse.Success(data = true)
    }
}