package com.john.auth.client.domain

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.time.LocalDateTime

/**
 * 등록된 클라이언트 정보
 *
 * @see <a href="https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql">Schema</a>
 * @author yoonho
 * @since 2023.05.11
 */
@Entity
@Table(catalog = "MP_CMS", name = "OAUTH2_REGISTERED_CLIENT")
class RegisteredClient(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    val id: Long = -1L,

    @Column(name = "NATIVE_CLIENT_ID", nullable = false)
    @Comment("Native 클라이언트 ID")
    var nativeClientId: String = "",
    @Column(name = "NATIVE_CLIENT_ID_ISSUED_AT", nullable = false)
    @Comment("클라이언트 ID 발급일자")
    var nativeClientIdIssuedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "REST_CLIENT_ID", nullable = false)
    @Comment("Rest 클라이언트 ID")
    var restClientId: String = "",
    @Column(name = "REST_CLIENT_ID_ISSUED_AT", nullable = false)
    @Comment("클라이언트 ID 발급일자")
    var restClientIdIssuedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "JS_CLIENT_ID", nullable = false)
    @Comment("js 클라이언트 ID")
    var jsClientId: String = "",
    @Column(name = "JS_CLIENT_ID_ISSUED_AT", nullable = false)
    @Comment("클라이언트 ID 발급일자")
    var jsClientIdIssuedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "ADMIN_CLIENT_ID", nullable = false)
    @Comment("admin 클라이언트 ID")
    var adminClientId: String = "",
    @Column(name = "ADMIN_CLIENT_ID_ISSUED_AT", nullable = false)
    @Comment("클라이언트 ID 발급일자")
    var adminClientIdIssuedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "CLIENT_SECRET")
    @Comment("클라이언트 SECRET")
    var clientSecret: String? = null,
    @Column(name = "CLIENT_SECRET_EXPIRES_AT")
    @Comment("클라이언트 SECRET 만료일자")
    val clientSecretExpiresAt: LocalDateTime? = null,

    @Column(name = "CLIENT_NAME", nullable = false)
    @Comment("클라이언트 명칭")
    var clientName: String = "",

    @Column(name = "CLIENT_AUTHENTICATION_METHODS", nullable = false)
    @Comment("클라이언트 인증방식")
    val clientAuthenticationMethods: String = "",
    @Column(name = "AUTHORIZATION_GRANT_TYPES", nullable = false)
    @Comment("권한부여 방식")
    val authorizationGrantTypes: String = "",

    @Column(name = "REDIRECT_URIS")
    @Comment("인증시 리다이렉트 URIs")
    var redirectUris: String? = null,
    @Column(name = "POST_LOGOUT_REDIRECT_URIS")
    @Comment("로그아웃시 리다이렉트 URIs")
    val postLogoutRedirectUris: String? = null,

    @Column(name = "SCOPES", nullable = false)
    @Comment("동의항목")
    val scopes: String = "",
)