package com.john.res.account.application.port.`in`

import com.john.res.account.adapter.`in`.web.dto.AuthInput

/**
 * @author yoonho
 * @since 2023.05.09
 */
interface LoginUseCase {

    /**
     * 로그인 프로세스
     *
     * @param input [AuthInput]
     * @author yoonho
     * @since 2023.05.10
     */
    fun login(input: AuthInput)
}