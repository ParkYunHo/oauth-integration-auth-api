package com.john.auth.client.adapter.out

import com.john.auth.client.adapter.`in`.web.dto.ClientUpdateInput
import com.john.auth.client.application.dto.RegisteredClientEntity
import com.john.auth.client.application.port.out.SavePort
import com.john.auth.client.domain.RegisteredClient
import com.john.auth.common.exception.BadRequestException
import com.john.auth.common.utils.ParseUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

/**
 * @author yoonho
 * @since 2023.05.11
 */
@Repository
class RegisteredClientPersistenceAdapter(
    private val registeredClientRepository: RegisteredClientRepository
): SavePort {
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

            val domain = ParseUtils.toDomain(input)
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
                .orElseThrow { throw BadRequestException("등록된 Client가 없습니다.") }

            // ClientName 체크
            if(input.clientName != registeredClient.clientName && !input.clientName.isNullOrEmpty()) {
                registeredClient.clientName = input.clientName
            }
            // RedirectUris 체크
            if(!input.redirectUris.isNullOrEmpty()) {
                registeredClient.redirectUris = input.redirectUris
            }

            return registeredClientRepository.save(registeredClient)
        } catch (e: Exception) {
            log.error(" >>> [update] Exception occurs - message: ${e.message}")
            throw BadRequestException("Client 정보수정에 실패하였습니다.")
        }
    }
}