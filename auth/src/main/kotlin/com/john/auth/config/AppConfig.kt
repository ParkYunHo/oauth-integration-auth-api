package com.john.auth.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.john.auth.common.utils.ParseUtils
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration

/**
 * @author yoonho
 * @since 2023.05.13
 */
@Configuration
class AppConfig(
    private val objectMapper: ObjectMapper
) {

    @PostConstruct
    fun init() {
        // ObjectMapper 세팅
        ParseUtils.setObjectMapper(objectMapper)
    }

}