package com.john.auth.authorization.adapter.out

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeInput
import com.john.auth.authorization.application.port.out.FindPort
import com.john.auth.client.adapter.out.RegisteredClientRepository
import com.john.auth.common.exception.InvalidClientException
import com.john.auth.common.exception.InvalidScopeException
import com.john.auth.common.exception.RedirectMismatchException
import com.john.auth.common.exception.UnsupportedResponseTypeException
import org.springframework.stereotype.Repository
import org.springframework.web.util.UriComponentsBuilder

/**
 * @author yoonho
 * @since 2023.05.13
 */
@Repository
class AuthorizationPersistenceAdapter(
    private val authorizationRepository: AuthorizationRepository,
    private val clientRepository: RegisteredClientRepository
): FindPort {

    /**
     * 인가코드 요청 Input 검사
     *
     * @param input [AuthorizationCodeInput]
     * @return [String]
     * @author yoonho
     * @since 2023.05.14
     */
    override fun checkClient(input: AuthorizationCodeInput) {
        val registeredClient = clientRepository.findByRestClientId(restClientId = input.client_id)
            .orElseThrow { throw InvalidClientException() }

        // grant_type 체크
        if(!registeredClient.authorizationGrantTypes.contains(input.response_type)) {
            throw UnsupportedResponseTypeException()
        }
        // scope 체크
        if(!registeredClient.scopes.contains(input.scope)) {
            throw InvalidScopeException()
        }
        // redirect_uri 체크
        val registeredClientRedirectUris = registeredClient.redirectUris ?: ""
        if(!registeredClientRedirectUris.contains(input.redirect_uri)) {
            throw RedirectMismatchException()
        }
    }
}