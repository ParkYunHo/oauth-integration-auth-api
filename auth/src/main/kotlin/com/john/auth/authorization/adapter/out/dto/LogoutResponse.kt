package com.john.auth.authorization.adapter.out.dto

import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.05.30
 */
data class LogoutResponse(
    val status: Int,
    val message: String,
    val data: Any?
): Serializable
