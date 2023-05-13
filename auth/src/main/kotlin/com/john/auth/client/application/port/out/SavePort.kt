package com.john.auth.client.application.port.out

import com.john.auth.client.adapter.`in`.web.dto.ClientUpdateInput
import com.john.auth.client.application.dto.RegisteredClientEntity
import com.john.auth.client.domain.RegisteredClient

/**
 * @author yoonho
 * @since 2023.05.11
 */
interface SavePort {

    /**
     * Client 등록
     *
     * @param input [RegisteredClientEntity]
     * @return [RegisteredClient]
     * @author yoonho
     * @since 2023.05.12
     */
    fun regist(input: RegisteredClientEntity): RegisteredClient

    /**
     * Client 정보수정
     *
     * @param input [ClientUpdateInput]
     * @return [RegisteredClient]
     * @author yoonho
     * @since 2023.05.13
     */
    fun update(input: ClientUpdateInput): RegisteredClient
}