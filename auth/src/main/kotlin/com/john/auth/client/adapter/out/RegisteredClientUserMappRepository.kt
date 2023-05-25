package com.john.auth.client.adapter.out

import com.john.auth.client.domain.RegisteredClientUserMapp
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

/**
 * @author yoonho
 * @since 2023.05.25
 */
interface RegisteredClientUserMappRepository: JpaRepository<RegisteredClientUserMapp, String> {
    fun findByClientIdAndUserId(clientId: String, userId: String): Optional<RegisteredClientUserMapp>
}