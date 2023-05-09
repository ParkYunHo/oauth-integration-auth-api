package com.john.res.common

import org.springframework.http.HttpStatus
import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.05.09
 */
data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val data: T
): Serializable {

    companion object {

        fun <T> Success(data: T): BaseResponse<T> =
            BaseResponse(
                status = HttpStatus.OK.value(),
                message = "Success",
                data = data
            )

    }
}
