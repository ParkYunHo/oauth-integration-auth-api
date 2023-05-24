package com.john.auth.authorization.application.dto

/**
 * @author yoonho
 * @since 2023.05.24
 */
data class IntrospectDto(
    val appUserId: String,
    val expiresIn: Long,
    val clientId: String
)
