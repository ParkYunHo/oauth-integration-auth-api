package com.john.auth.consent.adapter.out

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeRedirectInput
import com.john.auth.common.exception.InvalidScopeException
import com.john.auth.common.exception.NotFoundException
import com.john.auth.consent.application.port.out.ConsentFindPort
import com.john.auth.consent.application.port.out.ConsentSavePort
import com.john.auth.consent.domain.Consent
import com.john.auth.consent.domain.ConsentPk
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

/**
 * @author yoonho
 * @since 2023.05.19
 */
@Repository
class ConsentPersistenceAdapter(
    private val consentRepositoryImpl: ConsentRepositoryImpl
): ConsentFindPort, ConsentSavePort {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * Consent 조회
     *
     * @param input [AuthorizationCodeRedirectInput]
     * @return [Boolean]
     * @author yoonho
     * @since 2023.05.19
     */
    override fun findConsent(input: AuthorizationCodeRedirectInput): Boolean {
        val registeredConsent = consentRepositoryImpl.findConsent(clientId = input.client_id, userId = input.userId!!)

        if(registeredConsent == null) {
            // Consent 미등록시 등록화면으로 이동
            return false
        }else {
            // 올바른 scope가 아닐 경우 Exception throw
            if(!registeredConsent.authorities.contains(input.scope)) {
                throw InvalidScopeException()
            }
        }

        return true
    }

    /**
     * Consent 등록
     *
     * @param input [AuthorizationCodeRedirectInput]
     * @author yoonho
     * @since 2023.05.19
     */
    override fun saveConsent(input: AuthorizationCodeRedirectInput) {
        consentRepositoryImpl.saveConsent(input = input)
    }
}