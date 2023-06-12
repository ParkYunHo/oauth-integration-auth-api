package com.john.auth.authorization.application.port.out

import com.john.auth.authorization.domain.Authorization
import com.john.auth.client.domain.RegisteredClient

/**
 * @author yoonho
 * @since 2023.05.14
 */
interface AuthorizationFindPort {

    /**
     * Client 인증
     *
     * @param clientId [String]
     * @param clientSecret [String]
     * @author yoonho
     * @since 2023.05.14
     */
    fun checkClient(clientId: String, clientSecret: String): RegisteredClient

    /**
     * 인가코드 체크
     *
     * @param clientId [String]
     * @param authorizationCode [String]
     * @return [Authorization]
     * @author yoonho
     * @since 2023.05.24
     */
    fun checkAuthorizationCode(clientId: String, authorizationCode: String): Authorization

    /**
     * RefreshToken 체크
     *
     * @param clientId [String]
     * @param refreshToken [String]
     * @return [Authorization]
     * @author yoonho
     * @since 2023.05.24
     */
    fun checkRefreshToken(clientId: String, refreshToken: String): Authorization

    /**
     * AccessToken 체크
     *
     * @param accessToken [String]
     * @return [Authorization]
     * @author yoonho
     * @since 2023.05.24
     */
    fun checkAccessToken(accessToken: String): Authorization
}