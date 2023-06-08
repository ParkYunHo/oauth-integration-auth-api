package com.john.auth.client.application.port.out

import com.john.auth.client.domain.RegisteredClientUserMapp

/**
 * @author yoonho
 * @since 2023.05.19
 */
interface FindPort {

    /**
     * Client의 Scopes 조회
     *
     * @param clientId [String]
     * @param clientSecret [String]
     * @return [String]
     * @author yoonho
     * @since 2023.05.19
     */
    fun findScopes(clientId: String, clientSecret: String): String

    /**
     * AppUserId 조회
     *
     * @param clientId [String]
     * @param userId [String]
     * @return [RegisteredClientUserMapp]
     * @author yoonho
     * @since 2023.05.25
     */
    fun findAppUserId(clientId: String, userId: String): RegisteredClientUserMapp
}