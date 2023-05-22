package com.john.auth.authorization.application.dto

import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.05.22
 */
data class IssueTokenDto(
    val authorizationCode: String? = "",

    val accessToken: String,
    val accessTokenIssuedAt: LocalDateTime,
    val accessTokenExpiresAt: LocalDateTime,
    val accessTokenType: String,
    val accessTokenScopes: String,

    val refreshTokenValue: String,
    val refreshTokenIssuedAt: LocalDateTime,
    val refreshTokenExpiresAt: LocalDateTime,
)
