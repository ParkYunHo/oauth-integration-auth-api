package com.john.auth.authorization.adapter.out

import com.john.auth.authorization.domain.Authorization
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author yoonho
 * @since 2023.05.13
 */
interface AuthorizationRepository: JpaRepository<Authorization, Long> {
}