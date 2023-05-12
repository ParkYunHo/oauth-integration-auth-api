package com.john.auth.client.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment
import java.time.LocalDateTime

/**
 * 클라이언트에 연동된 사용자 정보
 *
 * @author yoonho
 * @since 2023.05.12
 */
@Entity
@Table(name = "OAUTH2_REGISTERED_CLIENT_USER_MAPP")
class RegisteredClientUserMapp(
    @Id
    @Column(name = "CLIENT_ID", nullable = false)
    @Comment("클라이언트 ID")
    val clientId: String = "",
    @Id
    @Column(name = "USER_ID", nullable = false)
    @Comment("사용자 ID")
    val userId: String = "",

    @Column(name = "CREATED_AT", nullable = false)
    @Comment("등록일자")
    val createdAt: LocalDateTime = LocalDateTime.now()
)