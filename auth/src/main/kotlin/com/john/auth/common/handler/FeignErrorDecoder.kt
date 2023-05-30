package com.john.auth.common.handler

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.john.auth.authorization.adapter.out.dto.LogoutResponse
import com.john.auth.client.domain.RegisteredClient
import com.john.auth.common.exception.BadRequestException
import com.john.auth.common.exception.InternalServerException
import com.john.auth.common.utils.ParseUtils
import feign.Response
import feign.codec.ErrorDecoder
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import java.io.Serializable
import java.lang.Exception

/**
 * @author yoonho
 * @since 2023.05.28
 */
class FeignErrorDecoder: ErrorDecoder {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun decode(methodKey: String?, response: Response): Exception {
        val status = HttpStatus.resolve(response.status()) ?: HttpStatus.BAD_REQUEST

        if(status.is4xxClientError) {
            log.warn(" >>> [decode] 4xx Client Error - methodKey: $methodKey, status: ${response.status()}")
            throw BadRequestException()
        }else if(status.is5xxServerError) {
            log.warn(" >>> [decode] 5xx Server Error - methodKey: $methodKey, status: ${response.status()}")
            throw InternalServerException()
        }

        return ErrorDecoder.Default().decode(methodKey, response)
    }
}