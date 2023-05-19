package com.john.auth.client.application

import com.john.auth.client.adapter.`in`.web.dto.ClientReIssueInput
import com.john.auth.client.adapter.`in`.web.dto.ClientRegistInput
import com.john.auth.client.adapter.`in`.web.dto.ClientUpdateInput
import com.john.auth.client.application.dto.RegisteredClientEntity
import com.john.auth.client.application.port.`in`.DeleteUseCase
import com.john.auth.client.application.port.`in`.FindScopesUseCase
import com.john.auth.client.application.port.`in`.RegistUseCase
import com.john.auth.client.application.port.`in`.UpdateUseCase
import com.john.auth.client.application.port.out.DeletePort
import com.john.auth.client.application.port.out.FindPort
import com.john.auth.client.application.port.out.SavePort
import com.john.auth.common.constants.ReIssueType
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
    private val savePort: SavePort,
    private val deletePort: DeletePort,
    private val findPort: FindPort
): RegistUseCase, UpdateUseCase, DeleteUseCase, FindScopesUseCase {

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

        val registeredClient = savePort.regist(input = entity)
        return ParseUtils.toEntity(domain = registeredClient)
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
        val updatedClient = savePort.update(input = input)
        return ParseUtils.toEntity(domain = updatedClient)
    }

    /**
     * Client Key정보 재발급
     *
     * @param input [ClientReIssueInput]
     * @return [RegisteredClientEntity]
     * @author yoonho
     * @since 2023.05.13
     */
    override fun reIssue(input: ClientReIssueInput): RegisteredClientEntity {
        val updatedClient = savePort.reIssue(input = input)
        return ParseUtils.toEntity(domain = updatedClient)
    }

    /**
     * Client 삭제
     *
     * @param input [Long]
     * @author yoonho
     * @since 2023.05.13
     */
    override fun delete(input: Long) =
        deletePort.delete(input = input)


    /**
     * Client의 Scopes 조회
     *
     * @param clientId [String]
     * @return [String]
     * @author yoonho
     * @since 2023.05.19
     */
    override fun findScopes(clientId: String): String =
        findPort.findScopes(clientId = clientId)
}