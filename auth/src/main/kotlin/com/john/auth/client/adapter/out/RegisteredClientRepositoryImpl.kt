package com.john.auth.client.adapter.out

import com.john.auth.client.domain.QRegisteredClient
import com.john.auth.client.domain.RegisteredClient
import com.john.auth.common.repository.PocRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * @author yoonho
 * @since 2023.06.12
 */
@Repository
class RegisteredClientRepositoryImpl(
    @Qualifier("pocQueryFactory") private val queryFactory: JPAQueryFactory
): PocRepository {

    private val registeredClient = QRegisteredClient.registeredClient!!

    @Transactional(readOnly = true)
    fun findRegisteredClientByClientInfo(clientId: String, clientSecret: String): RegisteredClient? =
            queryFactory
                    .selectFrom(registeredClient)
                    .where(
                            registeredClient.restClientId.eq(clientId),
                            registeredClient.clientSecret.eq(clientSecret)
                    )
                    .fetchFirst()

}