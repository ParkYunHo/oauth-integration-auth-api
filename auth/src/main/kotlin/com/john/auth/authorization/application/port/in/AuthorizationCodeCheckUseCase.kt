package com.john.auth.authorization.application.port.`in`

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeInput

/**
 * @author yoonho
 * @since 2023.05.14
 */
interface AuthorizationCodeCheckUseCase {

    /**
     * 인가코드 요청 Input 검사
     *
     * @param input [AuthorizationCodeInput]
     * @return [String]
     * @author yoonho
     * @since 2023.05.14
     */
    fun check(input: AuthorizationCodeInput): String
}