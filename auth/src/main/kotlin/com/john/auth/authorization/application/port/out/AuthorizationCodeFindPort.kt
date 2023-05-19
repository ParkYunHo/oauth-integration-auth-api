package com.john.auth.authorization.application.port.out

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeInput

/**
 * @author yoonho
 * @since 2023.05.14
 */
interface AuthorizationCodeFindPort {

    /**
     * 인가코드 요청 Input 검사
     *
     * @param input [AuthorizationCodeInput]
     * @author yoonho
     * @since 2023.05.14
     */
    fun checkClient(input: AuthorizationCodeInput)
}