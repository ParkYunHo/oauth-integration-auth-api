package com.john.auth.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.john.auth.common.utils.EnvironmentUtils
import com.john.auth.common.utils.ParseUtils
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

/**
 * @author yoonho
 * @since 2023.05.13
 */
@Configuration
class AppConfig(
    private val objectMapper: ObjectMapper,
    private val env: Environment
) {

    @PostConstruct
    fun init() {
        // ObjectMapper 세팅
        ParseUtils.setObjectMapper(objectMapper = objectMapper)
        // EnvironmentUtils 세팅
        EnvironmentUtils.setEnvironment(env = env)
    }
}