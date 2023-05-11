package com.john.auth.client.adapter.out

import com.john.auth.client.domain.RegisteredClient
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author yoonho
 * @since 2023.05.11
 */
interface RegisteredClientRepository: JpaRepository<RegisteredClient, Long>