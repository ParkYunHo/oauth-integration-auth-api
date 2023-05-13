package com.john.auth.client.application.dto

import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.05.12
 */
data class RegisteredClientEntity(
    val nativeClientId: String = "",
    val nativeClientIdIssuedAt: LocalDateTime = LocalDateTime.now(),
    val restClientId: String = "",
    val restClientIdIssuedAt: LocalDateTime = LocalDateTime.now(),
    val jsClientId: String = "",
    val jsClientIdIssuedAt: LocalDateTime = LocalDateTime.now(),
    val adminClientId: String = "",
    val adminClientIdIssuedAt: LocalDateTime = LocalDateTime.now(),

    val clientSecret: String? = null,
    val clientSecretExpiresAt: LocalDateTime? = LocalDateTime.MAX,
    val clientName: String = "",
    val clientAuthenticationMethods: String = "",
    val authorizationGrantTypes: String = "",
    val redirectUris: String? = null,
    val postLogoutRedirectUris: String? = null,
    val scopes: String = "",
)