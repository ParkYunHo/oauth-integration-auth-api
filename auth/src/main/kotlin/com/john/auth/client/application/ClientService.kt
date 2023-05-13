package com.john.auth.client.application

import com.john.auth.client.adapter.`in`.web.dto.ClientRegistInput
import com.john.auth.client.adapter.`in`.web.dto.ClientUpdateInput
import com.john.auth.client.application.dto.RegisteredClientEntity
import com.john.auth.client.application.port.`in`.RegistUseCase
import com.john.auth.client.application.port.`in`.UpdateUseCase
import com.john.auth.client.application.port.out.SavePort
import com.john.auth.common.utils.Base64StringKeyGenerator
import com.john.auth.common.utils.ParseUtils
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.05.11
 */
@Service
class ClientService(
    private val savePort: SavePort
): RegistUseCase, UpdateUseCase {

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
            nativeClientIdIssuedAt = LocalDateTime.now(),
            restClientId = Base64StringKeyGenerator.generateKey(keyLength = 32),
            restClientIdIssuedAt = LocalDateTime.now(),
            jsClientId = Base64StringKeyGenerator.generateKey(keyLength = 32),
            jsClientIdIssuedAt = LocalDateTime.now(),
            adminClientId = Base64StringKeyGenerator.generateKey(keyLength = 32),
            adminClientIdIssuedAt = LocalDateTime.now(),
            clientSecret = Base64StringKeyGenerator.generateKey(keyLength = 32),
            clientSecretExpiresAt = null,
            clientName = input.clientName,
            clientAuthenticationMethods = "client_secret_post",
            authorizationGrantTypes = "authorization_code",
            redirectUris = input.redirectUris,
            postLogoutRedirectUris = null,
            scopes = input.scopes
        )

        val registeredClient = savePort.regist(entity)
        return ParseUtils.toEntity(registeredClient)
    }

    /**
     * Client 정보수정
     *
     * @param input [ClientUpdateInput]
     * @return [RegisteredClientEntity]
     * @author yoonho
     * @since 2023.05.13
     */
    override fun update(input: ClientUpdateInput): RegisteredClientEntity {
        val updatedClient = savePort.update(input)
        return ParseUtils.toEntity(updatedClient)
    }
}