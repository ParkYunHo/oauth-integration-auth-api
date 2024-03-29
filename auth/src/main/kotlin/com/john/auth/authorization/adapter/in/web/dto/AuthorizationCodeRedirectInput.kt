package com.john.auth.authorization.adapter.`in`.web.dto

import jakarta.validation.constraints.NotBlank
import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.05.19
 */
data class AuthorizationCodeRedirectInput(
    @field:NotBlank(message = "INVALID REDIRECT_URI")
    val redirect_uri: String,
    @field:NotBlank(message = "INVALID SCOPE")
    val scope: String,
    @field:NotBlank(message = "INVALID CLIENT_ID")
    val client_id: String,
    @field:NotBlank(message = "INVALID CLIENT_SECRET")
    val client_secret: String,

    val userId: String? = "",
    val state: String? = "",

    val code: String? = "",
    val error_description: String? = ""
): Serializable
