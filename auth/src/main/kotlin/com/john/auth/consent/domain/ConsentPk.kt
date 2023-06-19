package com.john.auth.consent.domain

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.io.Serializable

/**
 * 정보제공동의 정보 Primary Key
 *
 * @see <a href="https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql">Schema</a>
 * @author yoonho
 * @since 2023.05.19
 */
@Embeddable
class ConsentPk(
    @Column(name = "REGISTERED_CLIENT_ID", nullable = false)
    @Comment("등록된 클라이언트 ID")
    val registeredClientId: String = "",
    @Column(name = "PRINCIPAL_NAME", nullable = false)
    @Comment("사용자 명")
    val principalName: String = "",
): Serializable