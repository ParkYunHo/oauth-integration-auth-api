package com.john.res.account.adapter.out.persistence

import com.john.res.account.domain.Account
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author yoonho
 * @since 2023.03.04
 */
interface AccountRepository: JpaRepository<Account, String>