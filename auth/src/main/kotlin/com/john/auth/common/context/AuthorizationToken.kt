package com.john.auth.common.context

/**
 * @author yoonho
 * @since 2023.05.19
 */
data class AuthorizationToken(
    val redirect_uri: String? = "",
    val scope: String? = "",
    val client_id: String? = "",
    val userId: String? = "",
    val state: String? = "",
)