package com.john.auth.common.handler

import com.john.auth.common.BaseResponse
import com.john.auth.common.exception.*
import feign.FeignException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * @author yoonho
 * @since 2023.05.12
 */
@RestControllerAdvice
class ExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): BaseResponse<Void> {
        log.error(" >>> [MethodArgumentNotValidException] message: ${e.message}")

        val message = "Invalid Request Parameter"
        return BaseResponse(status = HttpStatus.BAD_REQUEST.value(), message = message, data = null)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun httpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): BaseResponse<Void> {
        log.error(" >>> [HttpRequestMethodNotSupportedException] message: ${e.message}")

        val message = "Unsupported Media Type"
        return BaseResponse(status = HttpStatus.BAD_REQUEST.value(), message = message, data = null)
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun httpMediaTypeNotSupportedException(request: HttpServletRequest, e: HttpMediaTypeNotSupportedException): BaseResponse<Void> {
        log.error(" >>> [HttpMediaTypeNotSupportedException] message: ${e.message}")

        val message = "Content-Type '${request.contentType ?: ""}' is not supported"
        return BaseResponse(status = HttpStatus.BAD_REQUEST.value(), message = message, data = null)
    }

    @ExceptionHandler(BindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun bindException(e: BindException): BaseResponse<Void> {
        log.error(" >>> [BindException] message: ${e.message}")

        val message = "Invalid Request Parameter"
        return BaseResponse(status = HttpStatus.BAD_REQUEST.value(), message = message, data = null)
    }

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequestException(e: BadRequestException): BaseResponse<Void> {
        val message =
            if(e.message.isNullOrEmpty()) {
                "Invalid Request Parameter"
            }else {
                e.message!!
            }

        log.error(" >>> [BadRequestException] message: ${e.message}")
        return BaseResponse(status = HttpStatus.BAD_REQUEST.value(), message = message, data = null)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun notFoundException(e: NotFoundException): BaseResponse<Void> {
        val message =
            if(e.message.isNullOrEmpty()) {
                "Not Found Data"
            }else {
                e.message!!
            }

        log.error(" >>> [NotFoundException] message: ${e.message}")
        return BaseResponse(status = HttpStatus.BAD_REQUEST.value(), message = message, data = null)
    }

    @ExceptionHandler(UnsupportedResponseTypeException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun unsupportedResponseTypeException(e: UnsupportedResponseTypeException): BaseResponse<Void> {
        log.error(" >>> [UnsupportedResponseTypeException] message: ${e.message}")

        val message = "Unsupported response type"
        return BaseResponse(status = HttpStatus.BAD_REQUEST.value(), message = message, data = null)
    }

    @ExceptionHandler(RedirectMismatchException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun redirectMismatchException(e: RedirectMismatchException): BaseResponse<Void> {
        log.error(" >>> [RedirectMismatchException] message: ${e.message}")

        val message = "RedirectUris mismatch"
        return BaseResponse(status = HttpStatus.BAD_REQUEST.value(), message = message, data = null)
    }

    @ExceptionHandler(InvalidScopeException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun invalidScopeException(e: InvalidScopeException): BaseResponse<Void> {
        log.error(" >>> [InvalidScopeException] message: ${e.message}")

        val message = "Invalid scope"
        return BaseResponse(status = HttpStatus.BAD_REQUEST.value(), message = message, data = null)
    }

    @ExceptionHandler(InvalidClientException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun invalidClientException(e: InvalidClientException): BaseResponse<Void> {
        log.error(" >>> [InvalidClientException] message: ${e.message}")

        val message = "Invalid Client"
        return BaseResponse(status = HttpStatus.BAD_REQUEST.value(), message = message, data = null)
    }

    @ExceptionHandler(InvalidTokenException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun invalidTokenException(e: InvalidTokenException): BaseResponse<Void> {
        log.error(" >>> [InvalidTokenException] message: ${e.message}")

        val message = "Invalid Token"
        return BaseResponse(status = HttpStatus.BAD_REQUEST.value(), message = message, data = null)
    }

    @ExceptionHandler(NotRegisteredUserException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun notRegisteredUserException(e: NotRegisteredUserException): BaseResponse<Void> {
        log.error(" >>> [NotRegisteredUserException] message: ${e.message}")

        val message = "Not Registered User"
        return BaseResponse(status = HttpStatus.BAD_REQUEST.value(), message = message, data = null)
    }

    @ExceptionHandler(InternalServerException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun internalServerException(e: InternalServerException): BaseResponse<Void> {
        log.error(" >>> [InternalServerException] message: ${e.message}")

        val message = "Internal Server Exception"
        return BaseResponse(status = HttpStatus.INTERNAL_SERVER_ERROR.value(), message = message, data = null)
    }
}