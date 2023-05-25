package com.john.auth.client.application.port.`in`

/**
 * @author yoonho
 * @since 2023.05.25
 */
interface FindAppUserIdUseCase {

    /**
     * AppUserId 조회
     *
     * @param clientId [String]
     * @param userId [String]
     * @return [String]
     * @author yoonho
     * @since 2023.05.25
     */
    fun findAppUserId(clientId: String, userId: String): String
}