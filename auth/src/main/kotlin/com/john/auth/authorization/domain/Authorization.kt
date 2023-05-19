package com.john.auth.authorization.domain

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.time.LocalDateTime

/**
 * 인증정보
 *
 * @see <a href="https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql">Schema</a>
 * @author yoonho
 * @since 2023.05.13
 */
@Entity
@Table(name = "OAUTH2_AUTHORIZATION")
class Authorization(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    val id: Long = -1L,

    @Column(name = "REGISTERED_CLIENT_ID", nullable = false)
    @Comment("등록된 클라이언트 ID")
    val registeredClientId: String = "",
    @Column(name = "PRINCIPAL_NAME", nullable = false)
    @Comment("사용자 명")
    val principalName: String = "",
    @Column(name = "AUTHORIZATION_GRANT_TYPE", nullable = false)
    @Comment("권한부여타입")
    val authorizationGrantType: String = "",
    @Column(name = "AUTHORIZED_SCOPES")
    @Comment("인증된 권한범위")
    val authorizedScopes: String? = null,
    @Column(name = "STATE")
    @Comment("state")
    val state: String? = null,

    @Lob
    @Column(name = "AUTHORIZATION_CODE_VALUE")
    @Comment("인가코드")
    val authorizationCodeValue: String? = null,
    @Column(name = "AUTHORIZATION_CODE_ISSUED_AT")
    @Comment("인가코드 발행일자")
    val authorizationCodeIssuedAt: LocalDateTime? = null,
    @Column(name = "AUTHORIZATION_CODE_EXPIRES_AT")
    @Comment("인가코드 만료일자")
    val authorizationCodeExpiresAt: LocalDateTime? = null,

    @Lob
    @Column(name = "ACCESS_TOKEN_VALUE")
    @Comment("엑세스토큰")
    val accessTokenValue: String? = null,
    @Column(name = "ACCESS_TOKEN_ISSUED_AT")
    @Comment("엑세스토큰 발행일자")
    val accessTokenIssuedAt: LocalDateTime? = null,
    @Column(name = "ACCESS_TOKEN_EXPIRES_AT")
    @Comment("엑세스토큰 만료일자")
    val accessTokenExpiresAt: LocalDateTime? = null,
    @Column(name = "ACCESS_TOKEN_TYPE")
    @Comment("엑세스토큰 유형")
    val accessTokenType: String? = null,
    @Column(name = "ACCESS_TOKEN_SCOPES")
    @Comment("엑세스토큰 권한범위")
    val accessTokenScopes: String? = null,

    @Lob
    @Column(name = "REFRESH_TOKEN_VALUE")
    @Comment("리프레시토큰")
    val refreshTokenValue: String? = null,
    @Column(name = "REFRESH_TOKEN_ISSUED_AT")
    @Comment("리프레시토큰 발행일자")
    val refreshTokenIssuedAt: LocalDateTime? = null,
    @Column(name = "REFRESH_TOKEN_EXPIRES_AT")
    @Comment("리프레시토큰 만료일자")
    val refreshTokenExpiresAt: LocalDateTime? = null,
)