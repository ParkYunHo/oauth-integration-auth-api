package com.john.auth.client.adapter.out

import com.john.auth.client.domain.QRegisteredClient
import com.john.auth.client.domain.QRegisteredClientUserMapp
import com.john.auth.client.domain.RegisteredClient
import com.john.auth.client.domain.RegisteredClientUserMapp
import com.john.auth.common.repository.PocRepository
import com.querydsl.core.types.dsl.BooleanExpression
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
    fun findRegisteredUserMapp(clientId: String, userId: String, appUserId: String): RegisteredClientUserMapp? =
        queryFactory
            .selectFrom(registeredClientUserMapp)
            .where(
                this.eqClientId(clientId = clientId),
                this.eqUserId(userId = userId),
                this.eqAppUserId(appUserId = appUserId)
            )
            .fetchFirst()

    private fun eqClientId(clientId: String): BooleanExpression? =
        if(clientId.isEmpty()) null
        else registeredClientUserMapp.clientId.eq(clientId)

    private fun eqUserId(userId: String): BooleanExpression? =
        if(userId.isEmpty()) null
        else registeredClientUserMapp.userId.eq(userId)

    private fun eqAppUserId(appUserId: String): BooleanExpression? =
        if(appUserId.isEmpty()) null
        else registeredClientUserMapp.appUserId.eq(appUserId)
}