package com.john.res

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ResApplication

fun main(args: Array<String>) {
    System.setProperty("spring.config.name", "application-res")
    runApplication<ResApplication>(*args)
}
