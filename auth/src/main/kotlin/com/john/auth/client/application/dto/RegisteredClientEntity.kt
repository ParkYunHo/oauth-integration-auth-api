package com.john.auth.client.application.dto

import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.05.12
 */
data class RegisteredClientEntity(
    val nativeClientId: String = "",
    val restClientId: String = "",
    val jsClientId: String = "",
    val adminClientId: String = "",
    val clientIdIssuedAt: LocalDateTime = LocalDateTime.now(),
    val clientSecret: String? = null,
    val clientSecretExpiresAt: LocalDateTime? = null,
    val clientName: String = "",
    val clientAuthenticationMethods: String = "",
    val authorizationGrantTypes: String = "",
    val redirectUris: String? = null,
    val postLogoutRedirectUris: String? = null,
    val scopes: String = "",
)