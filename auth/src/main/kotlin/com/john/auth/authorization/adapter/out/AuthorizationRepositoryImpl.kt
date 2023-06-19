package com.john.auth.authorization.adapter.out

import com.john.auth.authorization.application.dto.AuthorizationCodeDto
import com.john.auth.authorization.domain.Authorization
import com.john.auth.authorization.domain.QAuthorization
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
class AuthorizationRepositoryImpl(
    @Qualifier("pocQueryFactory") private val queryFactory: JPAQueryFactory
) : PocRepository {

    private val authorization = QAuthorization.authorization!!

    @Transactional
    fun insertAuthorization(input: AuthorizationCodeDto): Long =
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
    fun insertAuthorization(input: Authorization): Long =
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

    @Transactional
    fun updateAuthorizationToken(input: Authorization, hasRefreshToken: Boolean): Long {
        val query = queryFactory
            .update(authorization)

            .set(authorization.accessTokenValue, input.accessTokenValue)
            .set(authorization.accessTokenIssuedAt, input.accessTokenIssuedAt)
            .set(authorization.accessTokenExpiresAt, input.accessTokenExpiresAt)
            .set(authorization.accessTokenType, input.accessTokenType)
            .set(authorization.accessTokenScopes, input.accessTokenScopes)

            .where(authorization.id.eq(input.id))

        // RefreshToken 재발급 케이스에만 저장
        if(hasRefreshToken) {
            query
                .set(authorization.refreshTokenValue, input.refreshTokenValue)
                .set(authorization.refreshTokenIssuedAt, input.refreshTokenIssuedAt)
                .set(authorization.refreshTokenExpiresAt, input.refreshTokenExpiresAt)
        }

        return query.execute()
    }

    @Transactional
    fun updateAuthorizationExpiredAt(accessToken: String): Long =
        queryFactory
            .update(authorization)
            // access_token 만료처리
            .set(authorization.accessTokenExpiresAt, LocalDateTime.now())
            // refresh_token 만료처리
            .set(authorization.refreshTokenExpiresAt, LocalDateTime.now())
            // authorization_code 만료처리
            .set(authorization.authorizationCodeExpiresAt, LocalDateTime.now())

            .where(authorization.accessTokenValue.eq(accessToken))
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