package com.john.auth.consent.adapter.out

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeRedirectInput
import com.john.auth.common.exception.InvalidScopeException
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
    private val consentRepository: ConsentRepository
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
        val registeredConsent = consentRepository.findById(
            ConsentPk(
                registeredClientId = input.client_id,
                principalName = input.userId!!
            )
        )

        if(registeredConsent.isEmpty) {
            // Consent 미등록시 등록화면으로 이동
            return false
        }else {
            // 올바른 scope가 아닐 경우 Exception throw
            if(!registeredConsent.get().authorities.contains(input.scope)) {
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
        consentRepository.save(
            Consent(
                consentPk = ConsentPk(
                    registeredClientId = input.client_id,
                    principalName = input.userId!!
                ),
                authorities = input.scope
            )
        )
    }
}