package com.john.auth.authorization.adapter.out

import com.john.auth.authorization.application.dto.AuthorizationCodeDto
import com.john.auth.authorization.domain.Authorization
import com.john.auth.authorization.domain.QAuthorization
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
class AuthorizationRepositoryImpl(
    @Qualifier("pocQueryFactory") private val queryFactory: JPAQueryFactory
): PocRepository {

    private val authorization = QAuthorization.authorization!!

    @Transactional
    fun saveAuthorization(input: AuthorizationCodeDto): Long =
        queryFactory
                .insert(authorization)
                .columns(
                        authorization.registeredClientId,
                        authorization.principalName,
                        authorization.authorizationGrantType,
                        authorization.authorizedScopes,
                        authorization.state,
                        authorization.authorizationCodeValue,
                        authorization.authorizationCodeIssuedAt,
                        authorization.authorizationCodeExpiresAt,

                )
                .values(
                        input.clientId,
                        input.userId,
                        input.grantType,
                        input.scopes,
                        input.state,
                        input.authorizationCode,
                        input.authorizationCodeIssuedAt,
                        input.authorizationCodeExpiresAt
                )
                .execute()

    // TODO: 업데이트문으로 변경할 것!!!
    @Transactional
    fun saveAuthorization(input: Authorization): Long =
            queryFactory
                    .insert(authorization)
                    .columns(
                            authorization.registeredClientId,
                            authorization.principalName,
                            authorization.authorizationGrantType,
                            authorization.authorizedScopes,
                            authorization.state,
                            authorization.authorizationCodeValue,
                            authorization.authorizationCodeIssuedAt,
                            authorization.authorizationCodeExpiresAt,

                            )
                    .values(
                            input.registeredClientId,
                            input.principalName,
                            input.authorizationGrantType,
                            input.authorizedScopes,
                            input.state,
                            input.authorizationCodeValue,
                            input.authorizationCodeIssuedAt,
                            input.authorizationCodeExpiresAt
                    )
                    .execute()

    @Transactional(readOnly = true)
    fun findAuthorizationByCode(clientId: String, code: String): Authorization? =
            queryFactory
                    .selectFrom(authorization)
                    .where(
                            authorization.registeredClientId.eq(clientId),
                            authorization.authorizationCodeValue.eq(code)
                    )
                    .fetchFirst()

    @Transactional(readOnly = true)
    fun findAuthorizationByRefreshToken(clientId: String, refreshToken: String): Authorization? =
            queryFactory
                    .selectFrom(authorization)
                    .where(
                            authorization.registeredClientId.eq(clientId),
                            authorization.refreshTokenValue.eq(refreshToken)
                    )
                    .fetchFirst()

    @Transactional(readOnly = true)
    fun findAuthorizationByAccessToken(accessToken: String): Authorization? =
            queryFactory
                    .selectFrom(authorization)
                    .where(
                            authorization.accessTokenValue.eq(accessToken)
                    )
                    .fetchFirst()
}