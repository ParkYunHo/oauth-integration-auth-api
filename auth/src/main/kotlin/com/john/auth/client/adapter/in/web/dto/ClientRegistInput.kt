package com.john.auth.client.adapter.`in`.web.dto

import jakarta.validation.constraints.NotBlank

/**
 * @author yoonho
 * @since 2023.05.12
 */
data class ClientRegistInput(
    @field:NotBlank(message = "INVALID CLIENT_NAME")
    val clientName: String,
    val redirectUris: String? = "",
    val postLogoutRedirectUris: String? = "",
    @field:NotBlank(message = "INVALID SCOPE")
    val scopes: String
)
