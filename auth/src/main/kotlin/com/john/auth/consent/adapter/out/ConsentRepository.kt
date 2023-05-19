package com.john.auth.consent.adapter.out

import com.john.auth.consent.domain.Consent
import com.john.auth.consent.domain.ConsentPk
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author yoonho
 * @since 2023.05.19
 */
interface ConsentRepository: JpaRepository<Consent, ConsentPk>