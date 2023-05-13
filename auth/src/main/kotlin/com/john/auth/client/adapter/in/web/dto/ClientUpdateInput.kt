package com.john.auth.client.adapter.`in`.web.dto

import jakarta.validation.constraints.NotBlank

/**
 * @author yoonho
 * @since 2023.05.13
 */
data class ClientUpdateInput(
    @field:NotBlank(message = "INVALID CLIENT_ID")
    val clientId: String,
    val clientName: String? = "",
    val redirectUris: String? = "",
)
