package com.john.auth.authorization.adapter.`in`.web.dto

import jakarta.validation.constraints.NotBlank
import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.05.22
 */
data class AccessTokenInput(
    @field:NotBlank(message = "INVALID GRANT_TYPE")
    val grant_type: String,
    @field:NotBlank(message = "INVALID CLIENT_ID")
    val client_id: String,
    @field:NotBlank(message = "INVALID CLIENT_SECRET")
    val client_secret: String,
    @field:NotBlank(message = "INVALID REDIRECT_URI")
    val redirect_uri: String,

    // 인가코드를 통한 access_token 발급시 사용
    val code: String? = "",
    // refresh_token을 통한 access_token 재발급시 사용
    val refresh_token: String? = ""

): Serializable
