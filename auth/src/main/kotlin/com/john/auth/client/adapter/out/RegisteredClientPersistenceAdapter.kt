package com.john.auth.client.adapter.out

import com.john.auth.client.adapter.`in`.web.dto.ClientReIssueInput
import com.john.auth.client.adapter.`in`.web.dto.ClientUpdateInput
import com.john.auth.client.application.dto.RegisteredClientEntity
import com.john.auth.client.application.port.out.DeletePort
import com.john.auth.client.application.port.out.FindPort
import com.john.auth.client.application.port.out.SavePort
import com.john.auth.client.domain.RegisteredClient
import com.john.auth.common.constants.ReIssueType
import com.john.auth.common.exception.BadRequestException
import com.john.auth.common.exception.NotFoundException
import com.john.auth.common.utils.Base64StringKeyGenerator
import com.john.auth.common.utils.ParseUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.05.11
 */
@Repository
class RegisteredClientPersistenceAdapter(
    private val registeredClientRepository: RegisteredClientRepository
): SavePort, DeletePort, FindPort {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * Client 등록
     *
     * @param input [RegisteredClientEntity]
     * @return [RegisteredClient]
     * @author yoonho
     * @since 2023.05.12
     */
    override fun regist(input: RegisteredClientEntity): RegisteredClient {
        try {
            log.info(" >>> [regist] input: $input")

            val domain = ParseUtils.toDomain(entity = input)
            return registeredClientRepository.save(domain)
        } catch (e: Exception) {
            log.error(" >>> [regist] Exception occurs - message: ${e.message}")
            throw BadRequestException("Client 등록에 실패하였습니다.")
        }
    }

    /**
     * Client 정보수정
     *
     * @param input [ClientUpdateInput]
     * @return [RegisteredClient]
     * @author yoonho
     * @since 2023.05.13
     */
    override fun update(input: ClientUpdateInput): RegisteredClient {
        try {
            val registeredClient = registeredClientRepository.findById(input.clientId.toLong())
                .orElseThrow { throw NotFoundException("등록된 Client가 없습니다.") }

            // ClientName 체크
            if(input.clientName != registeredClient.clientName && !input.clientName.isNullOrEmpty()) {
                registeredClient.clientName = input.clientName
            }
            // RedirectUris 체크
            if(!input.redirectUris.isNullOrEmpty()) {
                registeredClient.redirectUris = input.redirectUris
            }

            return registeredClientRepository.save(registeredClient)
        } catch (ne: NotFoundException) {
            throw ne
        } catch (be: BadRequestException) {
            throw be
        } catch (e: Exception) {
            log.error(" >>> [update] Exception occurs - message: ${e.message}")
            throw BadRequestException("Client 정보수정에 실패하였습니다.")
        }
    }

    /**
     * Client Key정보 재발급
     *
     * @param input [ClientReIssueInput]
     * @return [RegisteredClient]
     * @author yoonho
     * @since 2023.05.13
     */
    override fun reIssue(input: ClientReIssueInput): RegisteredClient {
        try {
            val registeredClient = registeredClientRepository.findById(input.clientId.toLong())
                .orElseThrow { throw NotFoundException("등록된 Client가 없습니다.") }

            val reIssueValue = Base64StringKeyGenerator.generateKey(keyLength = 32)
            when(input.target) {
                // native
                ReIssueType.NATIVE.code -> {
                    registeredClient.nativeClientId = reIssueValue
                    registeredClient.nativeClientIdIssuedAt = LocalDateTime.now()
                }
                // rest
                ReIssueType.REST.code -> {
                    registeredClient.restClientId = reIssueValue
                    registeredClient.restClientIdIssuedAt = LocalDateTime.now()
                }
                // js
                ReIssueType.JAVASCRIPT.code -> {
                    registeredClient.jsClientId = reIssueValue
                    registeredClient.jsClientIdIssuedAt = LocalDateTime.now()
                }
                // admin
                ReIssueType.ADMIN.code -> {
                    registeredClient.adminClientId = reIssueValue
                    registeredClient.adminClientIdIssuedAt = LocalDateTime.now()
                }
                // client_secret
                ReIssueType.SECRET.code -> {
                    registeredClient.clientSecret = reIssueValue
                }
            }

            return registeredClientRepository.save(registeredClient)
        } catch (ne: NotFoundException) {
            throw ne
        } catch (be: BadRequestException) {
            throw be
        } catch (e: Exception) {
            log.error(" >>> [reIssue] Exception occurs - message: ${e.message}")
            throw BadRequestException("Client 정보수정에 실패하였습니다.")
        }
    }

    /**
     * Client 삭제
     *
     * @param input [Long]
     * @author yoonho
     * @since 2023.05.13
     */
    override fun delete(input: Long) {
        try {
            if(!registeredClientRepository.existsById(input)) {
                throw NotFoundException("등록된 Client가 없습니다.")
            }

            registeredClientRepository.deleteById(input)
        } catch (ne: NotFoundException) {
            throw ne
        } catch (e: Exception) {
            log.error(" >>> [delete] Exception occurs - message: ${e.message}")
            throw BadRequestException("Client 삭제에 실패하였습니다.")
        }
    }

    override fun findScopes(clientId: String, clientSecret: String): String =
        registeredClientRepository.findByRestClientIdAndClientSecret(
            restClientId = clientId,
            clientSecret = clientSecret
        )
            .orElseThrow { throw NotFoundException("등록된 Client가 없습니다.") }
            .scopes
}