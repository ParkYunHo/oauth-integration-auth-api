package com.john.auth.authorization.adapter.out

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeInput
import com.john.auth.authorization.application.dto.AuthorizationCodeDto
import com.john.auth.authorization.application.dto.IssueTokenDto
import com.john.auth.authorization.application.dto.TokenInfo
import com.john.auth.authorization.application.port.out.FindPort
import com.john.auth.authorization.application.port.out.SavePort
import com.john.auth.authorization.domain.Authorization
import com.john.auth.client.adapter.out.RegisteredClientRepository
import com.john.auth.client.domain.RegisteredClient
import com.john.auth.common.exception.*
import org.springframework.stereotype.Repository

/**
 * @author yoonho
 * @since 2023.05.13
 */
@Repository
class AuthorizationPersistenceAdapter(
    private val authorizationRepository: AuthorizationRepository,
    private val clientRepository: RegisteredClientRepository
): FindPort, SavePort {

    /**
     * 인가코드 요청 Input 검사
     *
     * @param clientId [String]
     * @param clientSecret [String]
     * @return [String]
     * @author yoonho
     * @since 2023.05.14
     */
    override fun checkClient(clientId: String, clientSecret: String): RegisteredClient =
        clientRepository.findByRestClientIdAndClientSecret(
            restClientId = clientId,
            clientSecret = clientSecret
        )
            .orElseThrow { throw InvalidClientException() }

    /**
     * 인가코드 저장
     *
     * @param input [AuthorizationCodeDto]
     * @author yoonho
     * @since 2023.05.19
     */
    override fun authorizationCodeRegister(input: AuthorizationCodeDto) {
        authorizationRepository.save(
            Authorization(
                registeredClientId = input.clientId,
                principalName = input.userId,
                authorizationGrantType = input.grantType,
                authorizedScopes = input.scopes,
                state = input.state,
                authorizationCodeValue = input.authorizationCode,
                authorizationCodeIssuedAt = input.authorizationCodeIssuedAt,
                authorizationCodeExpiresAt = input.authorizationCodeExpiresAt
            )
        )
    }

    override fun checkAuthorizationCode(clientId: String, authorizationCode: String): Authorization =
        authorizationRepository.findByRegisteredClientIdAndAuthorizationCodeValue(registeredClientId = clientId, authorizationCodeValue = authorizationCode)
            .orElseThrow { throw InvalidTokenException() }

    override fun tokenInfoRegister(authorization: Authorization) {
        authorizationRepository.save(authorization)
    }
}