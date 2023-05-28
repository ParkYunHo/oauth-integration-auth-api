package com.john.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import java.net.http.HttpClient
import java.time.Duration

/**
 * @author yoonho
 * @since 2023.05.27
 */
@Configuration
class HttpClientConfig(
    private val environment: Environment
) {

    /**
     * ResourceServer HttpClient
     *
     * @return [HttpClient]
     * @author yoonho
     * @since 2023.05.27
     */
    @Bean
    fun memberHttpClient(): HttpClient =
        HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NEVER)
            .connectTimeout(
                Duration.ofMillis(environment.getProperty("auth.conn.http-client.connect-timeout", "3000").toLong())
            )
            .build()
}