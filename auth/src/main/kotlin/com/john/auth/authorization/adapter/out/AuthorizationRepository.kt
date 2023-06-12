package com.john.auth.authorization.adapter.out

import com.john.auth.authorization.domain.Authorization
import com.john.auth.common.repository.PocRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * @author yoonho
 * @since 2023.05.13
 */
interface AuthorizationRepository: JpaRepository<Authorization, Long> {

    /**
     * Client 조회
     *
     * @param registeredClientId [String]
     * @param authorizationCodeValue [String]
     * @return [Optional]<[Authorization]>
     * @author yoonho
     * @since 2023.05.23
     */
    fun findByRegisteredClientIdAndAuthorizationCodeValue(registeredClientId: String, authorizationCodeValue: String): Optional<Authorization>

    /**
     * RefreshToken 조회
     *
     * @param registeredClientId [String]
     * @param refreshTokenValue [String]
     * @return [Optional]<[Authorization]>
     * @author yoonho
     * @since 2023.05.23
     */
    fun findByRegisteredClientIdAndRefreshTokenValue(registeredClientId: String, refreshTokenValue: String): Optional<Authorization>

    /**
     * AccessToken 조회
     *
     * @param accessTokenValue [String]
     * @return [Optional]<[Authorization]>
     * @author yoonho
     * @since 2023.05.24
     */
    fun findByAccessTokenValue(accessTokenValue: String): Optional<Authorization>
}