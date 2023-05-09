package com.john.res.account.application.port.`in`

import com.john.res.account.adapter.`in`.web.dto.AuthInput

/**
 * @author yoonho
 * @since 2023.05.09
 */
interface LoginUseCase {
    fun login(input: AuthInput): Boolean
}