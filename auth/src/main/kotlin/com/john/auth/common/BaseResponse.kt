package com.john.auth.common

import org.springframework.http.HttpStatus
import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.05.12
 */
data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val data: T?
): Serializable {

    companion object {

        fun <T> Success(data: T): BaseResponse<T> =
            BaseResponse(
                status = HttpStatus.OK.value(),
                message = "Success",
                data = data
            )

        fun SuccessNoContent(): BaseResponse<Void> =
            BaseResponse(
                status = HttpStatus.OK.value(),
                message = "SuccessNoContent",
                data = null
            )
    }
}
