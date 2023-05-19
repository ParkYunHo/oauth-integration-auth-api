package com.john.auth.authorization.application.port.out

import com.john.auth.authorization.application.dto.AuthorizationCodeDto

/**
 * @author yoonho
 * @since 2023.05.19
 */
interface AuthorizationCodeSavePort {

    /**
     * 인가코드 발급
     *
     * @param input [AuthorizationCodeDto]
     * @author yoonho
     * @since 2023.05.19
     */
    fun register(input: AuthorizationCodeDto)
}