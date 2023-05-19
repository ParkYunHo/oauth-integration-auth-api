package com.john.auth.consent.application

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeRedirectInput
import com.john.auth.consent.application.port.`in`.ConsentCheckUseCase
import com.john.auth.consent.application.port.`in`.ConsentSaveUseCase
import com.john.auth.consent.application.port.out.ConsentFindPort
import com.john.auth.consent.application.port.out.ConsentSavePort
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * @author yoonho
 * @since 2023.05.19
 */
@Service
class ConsentService(
    private val consentFindPort: ConsentFindPort,
    private val consentSavePort: ConsentSavePort
): ConsentCheckUseCase, ConsentSaveUseCase {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * Consent 조회
     *
     * @param input [AuthorizationCodeRedirectInput]
     * @return [Boolean]
     * @author yoonho
     * @since 2023.05.19
     */
    override fun checkConsent(input: AuthorizationCodeRedirectInput): Boolean =
        consentFindPort.findConsent(input)

    /**
     * Consent 등록
     *
     * @param input [AuthorizationCodeRedirectInput]
     * @author yoonho
     * @since 2023.05.19
     */
    override fun saveConsent(input: AuthorizationCodeRedirectInput) =
        consentSavePort.saveConsent(input)
}