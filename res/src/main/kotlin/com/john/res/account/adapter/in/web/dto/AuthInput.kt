package com.john.res.account.adapter.`in`.web.dto

import jakarta.validation.constraints.NotBlank

/**
 * @author yoonho
 * @since 2023.05.09
 */
data class AuthInput(
    @field:NotBlank(message = "INVALID USER_ID")
    val userId: String,
    @field:NotBlank(message = "INVALID PASSWORD")
    val password: String
)
