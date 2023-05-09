package com.john.res.account.domain

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.03.04
 */
@Entity
@Table(name = "USER_MGMT_TB")
class Account (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    val id: Long = -1L,
    @Column(name = "USER_ID", nullable = false)
    @Comment("사용자 ID")
    val userId: String? = "",
    @Column(name = "PASSWORD")
    @Comment("비밀번호")
    val password: String? = "",
    @Column(name = "NICKNAME")
    @Comment("닉네임")
    val nickName: String? = "",
    @Column(name = "EMAIL")
    @Comment("이메일")
    val email: String? = "",
    @Column(name = "BIRTHDAY")
    @Comment("생년월일")
    val birthday: String? = "",
    @Column(name = "GENDER")
    @Comment("성별")
    val gender: String? = "",
    @Column(name = "KOR_NAME")
    @Comment("한글명")
    val korName: String? = "",
    @Column(name = "UPDATED_AT")
    @Comment("업데이트일자")
    val updatedAt: LocalDateTime,
    @Column(name = "CREATED_AT")
    @Comment("생성일자")
    val createdAt: LocalDateTime = LocalDateTime.now()
)