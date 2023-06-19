package com.john.auth.authorization.adapter.out

import com.john.auth.authorization.application.dto.AuthorizationCodeDto
import com.john.auth.authorization.application.port.out.AuthorizationFindPort
import com.john.auth.authorization.application.port.out.AuthorizationSavePort
import com.john.auth.authorization.domain.Authorization
import com.john.auth.client.adapter.out.RegisteredClientRepositoryImpl
import com.john.auth.client.domain.RegisteredClient
import com.john.auth.common.exception.InvalidClientException
import com.john.auth.common.exception.InvalidTokenException
import org.springframework.stereotype.Component

/**
 * @author yoonho
 * @since 2023.05.13
 */
@Component
class AuthorizationPersistenceAdapter(
    private val authorizationRepositoryImpl: AuthorizationRepositoryImpl,
    private val registeredClientRepositoryImpl: RegisteredClientRepositoryImpl
): AuthorizationFindPort, AuthorizationSavePort {

    /**
     * Client 인증
     *
     * @param clientId [String]
     * @param clientSecret [String]
     * @return [String]
     * @author yoonho
     * @since 2023.05.14
     */
    override fun checkClient(clientId: String, clientSecret: String): RegisteredClient =
            registeredClientRepositoryImpl.findRegisteredClientByClientInfo(clientId = clientId, clientSecret = clientSecret)
                    ?: throw InvalidClientException()

    /**
     * 인가코드 저장
     *
     * @param input [AuthorizationCodeDto]
     * @author yoonho
     * @since 2023.05.19
     */
    override fun authorizationCodeRegister(input: AuthorizationCodeDto) {
        authorizationRepositoryImpl.insertAuthorization(input = input)
    }

    /**
     * 인가코드 체크
     *
     * @param clientId [String]
     * @param authorizationCode [String]
     * @return [Authorization]
     */
    override fun checkAuthorizationCode(clientId: String, authorizationCode: String): Authorization =
            authorizationRepositoryImpl.findAuthorizationByCode(clientId = clientId, code = authorizationCode)
                    ?: throw InvalidTokenException()

    /**
     * 토큰발급
     *
     * @param authorization [Authorization]
     * @param hasRefreshToken [Boolean]
     * @author yoonho
     * @since 2023.05.23
     */
    override fun tokenInfoRegister(authorization: Authorization, hasRefreshToken: Boolean) {
        authorizationRepositoryImpl.updateAuthorizationToken(input = authorization, hasRefreshToken = hasRefreshToken)
    }

    /**
     * RefreshToken 체크
     *
     * @param clientId [String]
     * @param refreshToken [String]
     * @return [Authorization]
     * @author yoonho
     * @since 2023.05.23
     */
    override fun checkRefreshToken(clientId: String, refreshToken: String): Authorization =
            authorizationRepositoryImpl.findAuthorizationByRefreshToken(clientId = clientId, refreshToken = refreshToken)
                    ?: throw InvalidTokenException()

    /**
     * AccessToken 체크
     *
     * @param accessToken [String]
     * @return [Authorization]
     * @author yoonho
     * @since 2023.05.24
     */
    override fun checkAccessToken(accessToken: String): Authorization =
            authorizationRepositoryImpl.findAuthorizationByAccessToken(accessToken = accessToken)
                    ?: throw InvalidTokenException()

    /**
     * 로그아웃
     *
     * @param userId [String]
     * @param accessToken [String]
     * @author yoonho
     * @since 2023.05.27
     */
    override fun logout(userId: String, accessToken: String) {
        authorizationRepositoryImpl.updateAuthorizationExpiredAt(accessToken = accessToken)
    }
}