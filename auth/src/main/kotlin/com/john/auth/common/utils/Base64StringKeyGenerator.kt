package com.john.auth.common.utils

import java.security.SecureRandom
import java.util.*

/**
 * @author yoonho
 * @since 2023.05.12
 */
object Base64StringKeyGenerator {

    /**
     * Base64 난수생성
     *
     * @param keyLength [Int]
     * @return [String]
     * @author yoonho
     * @since 2023.05.12
     */
    fun generateKey(keyLength: Int): String {
        val encoder = Base64.getUrlEncoder().withoutPadding()

        val random = SecureRandom()
        val bytes = ByteArray(keyLength)
        random.nextBytes(bytes)

        return String(encoder.encode(bytes))
    }
}