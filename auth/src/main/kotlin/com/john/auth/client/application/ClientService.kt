package com.john.auth.client.application

import com.john.auth.authorization.application.port.`in`.AccessTokenIntrospectUseCase
import com.john.auth.authorization.application.port.`in`.LogoutUseCase
import com.john.auth.authorization.application.port.out.AuthorizationFindPort
import com.john.auth.authorization.application.port.out.AuthorizationSavePort
import com.john.auth.client.adapter.`in`.web.dto.ClientReIssueInput
import com.john.auth.client.adapter.`in`.web.dto.ClientRegistInput
import com.john.auth.client.adapter.`in`.web.dto.ClientUpdateInput
import com.john.auth.client.application.dto.RegisteredClientEntity
import com.john.auth.client.application.port.`in`.*
import com.john.auth.client.application.port.out.ClientDeletePort
import com.john.auth.client.application.port.out.ClientFindPort
import com.john.auth.client.application.port.out.ClientSavePort
import com.john.auth.common.exception.NotFoundException
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
        private val clientSavePort: ClientSavePort,
        private val clientDeletePort: ClientDeletePort,
        private val clientFindPort: ClientFindPort,

        private val authorizationFindPort: AuthorizationFindPort,
        private val authorizationSavePort: AuthorizationSavePort,
): RegistUseCase,
    UpdateUseCase,
    DeleteUseCase,
    FindScopesUseCase,
    RegistAppUserIdUseCase,
    FindAppUserIdUseCase,
    UnlinkUseCase
{

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

        val registeredClient = clientSavePort.regist(input = entity)
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
        val updatedClient = clientSavePort.update(input = input)
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
        val updatedClient = clientSavePort.reIssue(input = input)
        return ParseUtils.toEntity(domain = updatedClient)
    }

    /**
     * Client 삭제
     *
     * @param clientId [Long]
     * @author yoonho
     * @since 2023.05.13
     */
    override fun delete(clientId: Long) =
        clientDeletePort.delete(clientId = clientId)


    /**
     * Client의 Scopes 조회
     *
     * @param clientId [String]
     * @param clientSecret [String]
     * @return [String]
     * @author yoonho
     * @since 2023.05.19
     */
    override fun findScopes(clientId: String, clientSecret: String): String =
        clientFindPort.findScopes(clientId = clientId, clientSecret = clientSecret)

    /**
     * AppUserId 등록
     *
     * @param clientId [String]
     * @param userId [String]
     * @return [String]
     * @author yoonho
     * @since 2023.05.25
     */
    override fun registAppUserId(clientId: String, userId: String): String {
        val appUserId = Base64StringKeyGenerator.generateKey(str = "$clientId$userId")
        clientSavePort.registAppUserId(clientId = clientId, userId = userId, appUserId = appUserId)

        return appUserId
    }

    /**
     * AppUserId 조회
     *
     * @param clientId [String]
     * @param userId [String]
     * @return [String]
     * @author yoonho
     * @since 2023.05.25
     */
    override fun findAppUserId(clientId: String, userId: String): String {
        val registeredClientUserMapp = clientFindPort.findAppUserId(clientId = clientId, userId = userId)
        if(registeredClientUserMapp.expiredAt != null && LocalDateTime.now().isAfter(registeredClientUserMapp.expiredAt)) {
            throw NotFoundException("등록된 AppUserId가 없습니다.")
        }

        return registeredClientUserMapp.appUserId
    }

    /**
     * Client Unlink
     *
     * @param clientId [Long]
     * @param accessToken [String]
     * @author yoonho
     * @since 2023.05.31
     */
    override fun unlink(clientId: Long, accessToken: String) {
        // accessToken으로 appUserId 조회
        val authorization = authorizationFindPort.checkAccessToken(accessToken = accessToken)
        val appUserId = this.findAppUserId(clientId = authorization.registeredClientId, userId = authorization.principalName)

        // appUserId로 연결된 clientId 연결끊기
        val registeredClientUserMapp = clientSavePort.unlinkAppUserId(appUserId = appUserId)

        // 로그아웃 처리 (access_token, refresh_token, authorization_code 만료처리)
        authorizationSavePort.logout(userId = registeredClientUserMapp.userId, accessToken = accessToken)
    }
}