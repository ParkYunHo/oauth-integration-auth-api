package com.john.res.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @author yoonho
 * @since 2023.03.02
 */
@Configuration
class WebConfig: WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name())
            .allowedHeaders("*")
            .maxAge(3000)
    }
}