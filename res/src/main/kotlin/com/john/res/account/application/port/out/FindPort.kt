package com.john.res.account.application.port.out

import com.john.res.account.adapter.`in`.web.dto.AuthInput

/**
 * @author yoonho
 * @since 2023.05.10
 */
interface FindPort {

    /**
     * 로그인 프로세스
     *
     * @param input [AuthInput]
     * @author yoonho
     * @since 2023.05.10
     */
    fun findUserIdAndPassword(input: AuthInput)
}