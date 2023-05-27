package com.john.auth.authorization.adapter.`in`.web.dto

import jakarta.validation.constraints.NotBlank
import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.05.27
 */
data class LogoutInput(
    @field:NotBlank(message = "INVALID TARGET_ID_TYPE")
    val target_id_type: String,
    @field:NotBlank(message = "INVALID TARGET_ID")
    val target_id: String,
): Serializable
