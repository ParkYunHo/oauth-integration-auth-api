package com.john.auth.consent.adapter.out

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeRedirectInput
import com.john.auth.client.domain.QRegisteredClient
import com.john.auth.client.domain.RegisteredClient
import com.john.auth.common.repository.PocRepository
import com.john.auth.consent.domain.Consent
import com.john.auth.consent.domain.QConsent
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * @author yoonho
 * @since 2023.06.13
 */
@Repository
class ConsentRepositoryImpl(
        @Qualifier("pocQueryFactory") private val queryFactory: JPAQueryFactory
) : PocRepository {

    private val consent = QConsent.consent!!

    @Transactional(readOnly = true)
    fun findConsent(clientId: String, userId: String): Consent? =
        queryFactory
            .selectFrom(consent)
            .where(
                consent.consentPk.registeredClientId.eq(clientId),
                consent.consentPk.principalName.eq(userId)
            )
            .fetchFirst()

    @Transactional
    fun saveConsent(input: AuthorizationCodeRedirectInput) {
        queryFactory
            .insert(consent)
            .columns(
                consent.consentPk.registeredClientId,
                consent.consentPk.principalName,
                consent.authorities
            )
            .values(
                input.client_id,
                input.userId!!,
                input.scope
            )
            .execute()
    }
}