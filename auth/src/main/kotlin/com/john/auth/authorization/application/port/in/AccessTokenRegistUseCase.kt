package com.john.auth.authorization.application.port.`in`

import com.john.auth.authorization.adapter.`in`.web.dto.AccessTokenInput
import com.john.auth.authorization.application.dto.TokenInfo

/**
 * @author yoonho
 * @since 2023.05.22
 */
interface AccessTokenRegistUseCase {

    /**
     * 엑세스토큰 발급
     *
     * @param input [AccessTokenInput]
     * @return [String]
     * @author yoonho
     * @since 2023.05.19
     */
    fun register(input: AccessTokenInput): TokenInfo
}