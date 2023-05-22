package com.john.auth.client.application.port.out

/**
 * @author yoonho
 * @since 2023.05.19
 */
interface FindPort {
    fun findScopes(clientId: String, clientSecret: String): String
}