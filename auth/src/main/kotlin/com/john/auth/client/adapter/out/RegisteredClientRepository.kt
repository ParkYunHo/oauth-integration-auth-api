package com.john.auth.client.adapter.out

import com.john.auth.client.domain.RegisteredClient
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

/**
 * @author yoonho
 * @since 2023.05.11
 */
interface RegisteredClientRepository: JpaRepository<RegisteredClient, Long> {
    fun findByRestClientId(restClientId: String): Optional<RegisteredClient>
}