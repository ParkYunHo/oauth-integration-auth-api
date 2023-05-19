package com.john.auth.consent.domain

import jakarta.persistence.*
import org.hibernate.annotations.Comment

/**
 * 정보제공동의 정보
 *
 * @see <a href="https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql">Schema</a>
 * @author yoonho
 * @since 2023.05.19
 */
@Entity
@Table(name = "OAUTH2_AUTHORIZATION_CONSENT")
class Consent(
    @EmbeddedId
    val consentPk: ConsentPk,

    @Column(name = "AUTHORITIES", nullable = false)
    @Comment("권한범위")
    val authorities: String = "",
)