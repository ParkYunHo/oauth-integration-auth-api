package com.john.auth.client.application

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.john.auth.client.adapter.`in`.web.dto.ClientRegistInput
import com.john.auth.client.application.dto.RegisteredClientEntity
import com.john.auth.client.application.port.`in`.RegistUseCase
import com.john.auth.client.application.port.out.SavePort
import com.john.auth.common.utils.Base64StringKeyGenerator
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.05.11
 */
@Service
class ClientService(
    private val savePort: SavePort,
    private val om: ObjectMapper
): RegistUseCase {

    /**
     * Client 등록
     *
     * @param input [ClientRegistInput]
     * @author yoonho
     * @since 2023.05.12
     */
    override fun regist(input: ClientRegistInput): RegisteredClientEntity {
        val entity = RegisteredClientEntity(
            nativeClientId = Base64StringKeyGenerator.generateKey(keyLength = 32),
            restClientId = Base64StringKeyGenerator.generateKey(keyLength = 32),
            jsClientId = Base64StringKeyGenerator.generateKey(keyLength = 32),
            adminClientId = Base64StringKeyGenerator.generateKey(keyLength = 32),
            clientIdIssuedAt = LocalDateTime.now(),
            clientSecret = Base64StringKeyGenerator.generateKey(keyLength = 32),
            clientSecretExpiresAt = LocalDateTime.MAX,
            clientName = input.clientName,
            clientAuthenticationMethods = "client_secret_post",
            authorizationGrantTypes = "authorization_code",
            redirectUris = input.redirectUris,
            postLogoutRedirectUris = null,
            scopes = input.scopes
        )

        val registeredClient = savePort.regist(entity)
        return om.convertValue(registeredClient, object: TypeReference<RegisteredClientEntity>() {})
    }
}