package com.john.auth.authorization.adapter.out

import com.john.auth.authorization.domain.Authorization
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

/**
 * @author yoonho
 * @since 2023.05.13
 */
interface AuthorizationRepository: JpaRepository<Authorization, Long> {
    fun findByRegisteredClientIdAndAuthorizationCodeValue(registeredClientId: String, authorizationCodeValue: String): Optional<Authorization>
}