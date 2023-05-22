package com.john.auth.client.application.port.`in`

/**
 * @author yoonho
 * @since 2023.05.19
 */
interface FindScopesUseCase {
    fun findScopes(clientId: String, clientSecret: String): String
}