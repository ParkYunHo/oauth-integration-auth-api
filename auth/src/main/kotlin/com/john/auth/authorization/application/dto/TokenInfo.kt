package com.john.auth.authorization.application.dto

/**
 * @author yoonho
 * @since 2023.05.22
 */
data class TokenInfo(
    val token_type: String,

    val access_token: String,
    val expires_in: Long,

    val refresh_token: String? = "",
    val refresh_token_expires_in: Long? = 0L,

    val scope: String? = ""
)
