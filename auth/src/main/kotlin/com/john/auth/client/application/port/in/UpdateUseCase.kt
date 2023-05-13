package com.john.auth.client.application.port.`in`

import com.john.auth.client.adapter.`in`.web.dto.ClientReIssueInput
import com.john.auth.client.adapter.`in`.web.dto.ClientUpdateInput
import com.john.auth.client.application.dto.RegisteredClientEntity

/**
 * @author yoonho
 * @since 2023.05.13
 */
interface UpdateUseCase {

    /**
     * Client 정보수정
     *
     * @param input [ClientUpdateInput]
     * @return [RegisteredClientEntity]
     * @author yoonho
     * @since 2023.05.13
     */
    fun update(input: ClientUpdateInput): RegisteredClientEntity

    /**
     * Client Key정보 재발급
     *
     * @param input [ClientReIssueInput]
     * @return [RegisteredClientEntity]
     * @author yoonho
     * @since 2023.05.13
     */
    fun reIssue(input: ClientReIssueInput): RegisteredClientEntity
}