package com.john.res.account.adapter.`in`.web.dto

import jakarta.validation.constraints.NotBlank

/**
 * @author yoonho
 * @since 2023.05.09
 */
data class RegisterInput(
    @field:NotBlank(message = "INVALID USER_ID")
    val userId: String,
    @field:NotBlank(message = "INVALID PASSWORD")
    val password: String,
    val nickname: String,
    val email: String,
    val birthday: String,
    val gender: String,
    val korName: String
)
