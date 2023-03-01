package com.john.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthApplication

fun main(args: Array<String>) {
    System.setProperty("spring.config.name", "application-auth")
    runApplication<AuthApplication>(*args)
}
