package com.john.auth.authorization.application.dto

import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.05.19
 */
data class AuthorizationCodeDto(
    val clientId: String,
    val userId: String,
    val grantType: String,
    val scopes: String,
    val state: String,
    val authorizationCode: String,
    val authorizationCodeIssuedAt: LocalDateTime,
    val authorizationCodeExpiresAt: LocalDateTime,
)
