package com.john.res.account.application.port.`in`

import com.john.res.account.adapter.`in`.web.dto.RegisterInput

/**
 * @author yoonho
 * @since 2023.05.09
 */
interface RegisterUseCase {

    /**
     * 회원가입 프로세스
     *
     * @param input [RegisterInput]
     * @return [Boolean]
     * @author yoonho
     * @since 2023.05.10
     */
    fun register(input: RegisterInput): Boolean
}