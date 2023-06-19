package com.john.auth.client.adapter.out

import com.john.auth.client.domain.QRegisteredClient
import com.john.auth.client.domain.RegisteredClient
import com.john.auth.common.constants.ReIssueType
import com.john.auth.common.repository.PocRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.06.12
 */
@Repository
class RegisteredClientRepositoryImpl(
        @Qualifier("pocQueryFactory") private val queryFactory: JPAQueryFactory
) : PocRepository {

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

    @Transactional
    fun insertRegisteredClient(input: RegisteredClient): Long =
        queryFactory
            .insert(registeredClient)
            .columns(
                registeredClient.nativeClientId,
                registeredClient.nativeClientIdIssuedAt,
                registeredClient.restClientId,
                registeredClient.restClientIdIssuedAt,
                registeredClient.jsClientId,
                registeredClient.jsClientIdIssuedAt,
                registeredClient.adminClientId,
                registeredClient.adminClientIdIssuedAt,
                registeredClient.clientSecret,
                registeredClient.clientSecretExpiresAt,
                registeredClient.clientName,
                registeredClient.clientAuthenticationMethods,
                registeredClient.authorizationGrantTypes,
                registeredClient.redirectUris,
                registeredClient.postLogoutRedirectUris,
                registeredClient.scopes
            )
            .values(
                input.nativeClientId,
                input.nativeClientIdIssuedAt,
                input.restClientId,
                input.restClientIdIssuedAt,
                input.jsClientId,
                input.jsClientIdIssuedAt,
                input.adminClientId,
                input.adminClientIdIssuedAt,
                input.clientSecret,
                input.clientSecretExpiresAt,
                input.clientName,
                input.clientAuthenticationMethods,
                input.authorizationGrantTypes,
                input.redirectUris,
                input.postLogoutRedirectUris,
                input.scopes
            )
            .execute()

    @Transactional
    fun updateRegisteredClient(input: RegisteredClient): Long =
        queryFactory
            .update(registeredClient)
            .set(registeredClient.clientName, input.clientName)
            .set(registeredClient.redirectUris, input.redirectUris)
            .execute()

    @Transactional
    fun updateRegisteredClientIdInfo(input: RegisteredClient, target: String): Long {
        val query = queryFactory
            .update(registeredClient)
            .where(registeredClient.id.eq(input.id))

        when(target) {
            // native
            ReIssueType.NATIVE.code -> {
                query
                    .set(registeredClient.nativeClientId, input.nativeClientId)
                    .set(registeredClient.nativeClientIdIssuedAt, input.nativeClientIdIssuedAt)
            }
            // rest
            ReIssueType.REST.code -> {
                query
                    .set(registeredClient.restClientId, input.restClientId)
                    .set(registeredClient.restClientIdIssuedAt, input.restClientIdIssuedAt)
            }
            // js
            ReIssueType.JAVASCRIPT.code -> {
                query
                    .set(registeredClient.jsClientId, input.jsClientId)
                    .set(registeredClient.jsClientIdIssuedAt, input.jsClientIdIssuedAt)
            }
            // admin
            ReIssueType.ADMIN.code -> {
                query
                    .set(registeredClient.adminClientId, input.adminClientId)
                    .set(registeredClient.adminClientIdIssuedAt, input.adminClientIdIssuedAt)
            }
            // client_secret
            ReIssueType.SECRET.code -> {
                query
                    .set(registeredClient.clientSecret, input.clientSecret)
            }
        }

        return query.execute()
    }

    @Transactional(readOnly = true)
    fun findRegisteredClientById(clientId: Long): RegisteredClient? =
        queryFactory
            .selectFrom(registeredClient)
            .where(
                registeredClient.id.eq(clientId),
            )
            .fetchFirst()

    @Transactional
    fun deleteById(clientId: Long): Long =
        queryFactory
            .delete(registeredClient)
            .where(registeredClient.id.eq(clientId))
            .execute()

}