package com.john.auth.common.handler

import com.john.auth.common.exception.BadRequestException
import com.john.auth.common.exception.InternalServerException
import com.john.auth.common.utils.ParseUtils
import feign.Response
import feign.codec.ErrorDecoder
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus

/**
 * @author yoonho
 * @since 2023.05.28
 */
class FeignErrorDecoder: ErrorDecoder {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * OpenFeign ErrorDecoder
     * <p>
     *     - 200응답이 아닌 경우 FeignException 발생
     *     - ErrorDecoder 클래스의 decode() 메서드 override
     *
     * @param methodKey [String]?
     * @param response [Response]
     * @return [Exception]
     * @author yoonho
     * @since 2023.05.31
     */
    override fun decode(methodKey: String?, response: Response): Exception {
        val status = HttpStatus.resolve(response.status()) ?: HttpStatus.BAD_REQUEST
        val body = ParseUtils.getInputStreamText(input = response.body().asInputStream())

        if(status.is4xxClientError) {
            log.warn(" >>> [decode] 4xx Client Error - methodKey: $methodKey, status: ${response.status()}, body: $body")
            throw BadRequestException()
        }else if(status.is5xxServerError) {
            log.warn(" >>> [decode] 5xx Server Error - methodKey: $methodKey, status: ${response.status()}, body: $body")
            throw InternalServerException()
        }

        return ErrorDecoder.Default().decode(methodKey, response)
    }
}