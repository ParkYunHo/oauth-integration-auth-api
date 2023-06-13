package com.john.auth.client.adapter.out

import com.john.auth.client.domain.QRegisteredClient
import com.john.auth.client.domain.QRegisteredClientUserMapp
import com.john.auth.client.domain.RegisteredClient
import com.john.auth.client.domain.RegisteredClientUserMapp
import com.john.auth.common.repository.PocRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.06.13
 */
@Repository
class RegisteredClientUserMappRepositoryImpl(
        @Qualifier("pocQueryFactory") private val queryFactory: JPAQueryFactory
) : PocRepository {

    private val registeredClientUserMapp = QRegisteredClientUserMapp.registeredClientUserMapp!!

    @Transactional
    fun insertRegisteredUserMapp(input: RegisteredClientUserMapp) {
        queryFactory
            .insert(registeredClientUserMapp)
            .columns(
                registeredClientUserMapp.appUserId,
                registeredClientUserMapp.clientId,
                registeredClientUserMapp.userId,
                registeredClientUserMapp.createdAt
            )
            .values(
                input.appUserId,
                input.clientId,
                input.userId,
                LocalDateTime.now()
            )
            .execute()
    }

    @Transactional
    fun updateRegisteredUserMappExpiredAt(appUserId: String): Long =
        queryFactory
            .update(registeredClientUserMapp)
            .set(registeredClientUserMapp.expiredAt, LocalDateTime.now())
            .where(registeredClientUserMapp.appUserId.eq(appUserId))
            .execute()

    @Transactional(readOnly = true)
    fun findRegisteredUserMappByClientIdAndUserId(clientId: String, userId: String): RegisteredClientUserMapp? =
        queryFactory
            .selectFrom(registeredClientUserMapp)
            .where(
                registeredClientUserMapp.clientId.eq(clientId),
                registeredClientUserMapp.userId.eq(userId)
            )
            .fetchFirst()

    @Transactional(readOnly = true)
    fun findRegisteredUserMappByClientIdAndUserId(appUserId: String): RegisteredClientUserMapp? =
        queryFactory
            .selectFrom(registeredClientUserMapp)
            .where(
                registeredClientUserMapp.appUserId.eq(appUserId)
            )
            .fetchFirst()
}