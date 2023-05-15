package com.john.auth.common.utils

import org.springframework.core.env.Environment

/**
 * @author yoonho
 * @since 2023.05.15
 */
object EnvironmentUtils {
    private lateinit var env: Environment

    /**
     * Environment DI
     *
     * @author yoonho
     * @since 2023.05.15
     */
    fun setEnvironment(env: Environment){
        this.env = env
    }

    /**
     * Properties 조회
     *
     * @author yoonho
     * @since 2023.05.15
     */
    fun getProperty(key: String): String? =
        this.env.getProperty(key)

    /**
     * Properties 조회 (default값 전달)
     *
     * @author yoonho
     * @since 2023.05.15
     */
    fun getProperty(key: String, defaultValue: String): String =
        this.env.getProperty(key, defaultValue)
}