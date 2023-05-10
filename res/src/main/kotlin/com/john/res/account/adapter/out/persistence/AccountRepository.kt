package com.john.res.account.adapter.out.persistence

import com.john.res.account.domain.Account
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

/**
 * @author yoonho
 * @since 2023.03.04
 */
interface AccountRepository: JpaRepository<Account, Long> {
    fun findByUserIdAndPassword(userId: String, password: String): Optional<Account>
}