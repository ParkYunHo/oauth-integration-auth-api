package com.john.auth.authorization.adapter.`in`.web.dto

import jakarta.validation.constraints.NotBlank
import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.05.13
 */
data class AuthorizationCodeInput(
    @field:NotBlank(message = "INVALID RESPONSE_TYPE")
    val response_type: String,
    @field:NotBlank(message = "INVALID CLIENT_ID")
    val client_id: String,
    @field:NotBlank(message = "INVALID SCOPE")
    val scope: String,
    @field:NotBlank(message = "INVALID REDIRECT_URI")
    val redirect_uri: String,

    val state: String? = "",
): Serializable
