package com.john.res.common.handler

import com.john.res.common.BaseResponse
import com.john.res.common.exception.NotRegisteredUserException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * @author yoonho
 * @since 2023.05.10
 */
@RestControllerAdvice
class ExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): BaseResponse<Void> {
        val message =
            if(e.message.isNullOrEmpty()) {
                "Invalid Request Parameter"
            }else {
                e.message!!
            }
        return BaseResponse(status = HttpStatus.BAD_REQUEST.value(), message = message, data = null)
    }

    @ExceptionHandler(NotRegisteredUserException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun notRegisteredUserException(e: NotRegisteredUserException): BaseResponse<Void> {
        val message =
            if(e.message.isNullOrEmpty()) {
                "Invalid Request Parameter"
            }else {
                e.message!!
            }
        return BaseResponse(status = HttpStatus.BAD_REQUEST.value(), message = message, data = null)
    }
}