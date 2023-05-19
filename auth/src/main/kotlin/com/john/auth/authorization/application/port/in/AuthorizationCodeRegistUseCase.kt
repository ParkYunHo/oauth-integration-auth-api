package com.john.auth.authorization.application.port.`in`

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeRedirectInput

/**
 * @author yoonho
 * @since 2023.05.19
 */
interface AuthorizationCodeRegistUseCase {

    /**
     * 인가코드 발급
     *
     * @param input [AuthorizationCodeRedirectInput]
     * @return [String]
     * @author yoonho
     * @since 2023.05.19
     */
    fun register(input: AuthorizationCodeRedirectInput): String
}