package com.john.auth.common.constants

/**
 * @author yoonho
 * @since 2023.05.28
 */
enum class RestCallClientType(
    val code: String,
    val desc: String
) {
    HTTPCLIENT("httpclient", "java.net.http.HttpClient"),
    OPENFEIGN("openfeign", "Spring Cloud OpenFeign"),
    ;
}