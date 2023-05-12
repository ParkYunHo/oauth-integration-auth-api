package com.john.auth.client.application.port.`in`

import com.john.auth.client.adapter.`in`.web.dto.ClientRegistInput
import com.john.auth.client.application.dto.RegisteredClientEntity

/**
 * @author yoonho
 * @since 2023.05.12
 */
interface RegistUseCase {

    /**
     * Client 등록
     *
     * @param input [ClientRegistInput]
     * @return [RegisteredClientEntity]
     * @author yoonho
     * @since 2023.05.12
     */
    fun regist(input: ClientRegistInput): RegisteredClientEntity
}