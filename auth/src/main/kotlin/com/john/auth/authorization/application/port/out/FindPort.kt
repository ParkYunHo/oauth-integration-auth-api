package com.john.auth.authorization.application.port.out

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeInput
import com.john.auth.authorization.domain.Authorization
import com.john.auth.client.domain.RegisteredClient

/**
 * @author yoonho
 * @since 2023.05.14
 */
interface FindPort {

    /**
     * 인가코드 요청 Input 검사
     *
     * @param clientId [String]
     * @param clientSecret [String]
     * @author yoonho
     * @since 2023.05.14
     */
    fun checkClient(clientId: String, clientSecret: String): RegisteredClient

    fun checkAuthorizationCode(clientId: String, authorizationCode: String): Authorization
}