package com.john.auth.client.application.port.`in`

/**
 * @author yoonho
 * @since 2023.05.19
 */
interface FindScopesUseCase {

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
}