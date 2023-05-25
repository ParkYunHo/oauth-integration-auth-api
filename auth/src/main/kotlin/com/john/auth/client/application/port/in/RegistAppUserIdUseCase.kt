package com.john.auth.client.application.port.`in`

/**
 * @author yoonho
 * @since 2023.05.25
 */
interface RegistAppUserIdUseCase {

    /**
     * AppUserId 등록
     *
     * @param clientId [String]
     * @param userId [String]
     * @return [String]
     * @author yoonho
     * @since 2023.05.25
     */
    fun registAppUserId(clientId: String, userId: String): String
}