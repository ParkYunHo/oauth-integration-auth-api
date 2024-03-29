package com.john.auth

import com.john.auth.common.utils.Base64StringKeyGenerator
import io.kotest.core.spec.style.BehaviorSpec
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class Test: BehaviorSpec({
    val log = LoggerFactory.getLogger(this::class.java)

    Given("TEST") {
//        val now = LocalDateTime.now()
//        val t = LocalDateTime.of(2023, 5, 25, 4, 45, 29, 897932)
//        val t = LocalDateTime.now().plusHours(4)

        // 2023-05-25T04:45:29.897932

//        val diff = Duration.between(now.toLocalTime(), t.toLocalTime())
//        log.info(" >>> diff - hours: ${diff.toHours()}, min: ${diff.toMinutes()}, sec: ${diff.toSeconds()}")

//        log.info(" >>> diff - hours: ${ChronoUnit.HOURS.between(now, t)}, min: ${ChronoUnit.MINUTES.between(now, t)}, sec: ${ChronoUnit.SECONDS.between(now, t)}")

//        val clientId = "SQKUiBl0GDaQOxN9wDV9ZGbcTNNcwDvYTz0sSUxgRPM"
//        val userId = "admin"
//        val t = Base64StringKeyGenerator.generateKey("$clientId$userId")
//        log.info(" >>> t : $t")

        for(i: Int in 1 until 3) {
            log.info(" >>> [test] $i")
        }
    }

})