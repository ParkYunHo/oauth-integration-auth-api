package com.john.auth.client.application.port.out

import com.john.auth.client.adapter.`in`.web.dto.ClientReIssueInput
import com.john.auth.client.adapter.`in`.web.dto.ClientUpdateInput
import com.john.auth.client.application.dto.RegisteredClientEntity
import com.john.auth.client.domain.RegisteredClient
import com.john.auth.client.domain.RegisteredClientUserMapp

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

    /**
     * Client Key정보 재발급
     *
     * @param input [ClientReIssueInput]
     * @return [RegisteredClient]
     * @author yoonho
     * @since 2023.05.13
     */
    fun reIssue(input: ClientReIssueInput): RegisteredClient

    /**
     * AppUserId 등록
     *
     * @param clientId [String]
     * @param userId [String]
     * @param appUserId [String]
     * @author yoonho
     * @since 2023.05.25
     */
    fun registAppUserId(clientId: String, userId: String, appUserId: String)

    /**
     * AppUserId Unlink
     *
     * @param appUserId [String]
     * @return [RegisteredClientUserMapp]
     * @author yoonho
     * @since 2023.06.08
     */
    fun unlinkAppUserId(appUserId: String): RegisteredClientUserMapp
}