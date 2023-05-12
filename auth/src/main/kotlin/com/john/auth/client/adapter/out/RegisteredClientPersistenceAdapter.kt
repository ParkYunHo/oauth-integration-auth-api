package com.john.auth.client.adapter.out

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.john.auth.client.application.dto.RegisteredClientEntity
import com.john.auth.client.application.port.out.SavePort
import com.john.auth.client.domain.RegisteredClient
import com.john.auth.common.exception.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

/**
 * @author yoonho
 * @since 2023.05.11
 */
@Repository
class RegisteredClientPersistenceAdapter(
    private val registeredClientRepository: RegisteredClientRepository,
    private val om: ObjectMapper
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

            val domain = om.convertValue(input, object: TypeReference<RegisteredClient>() {})
            return registeredClientRepository.save(domain)
        } catch (e: Exception) {
            log.error(" >>> [regist] message: ${e.message}")
            throw BadRequestException("Client 등록에 실패하였습니다.")
        }
    }
}