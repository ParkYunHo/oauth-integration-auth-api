package com.john.auth.consent.application.port.out

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeRedirectInput

/**
 * @author yoonho
 * @since 2023.05.19
 */
interface ConsentFindPort {
    fun findConsent(input: AuthorizationCodeRedirectInput): Boolean
}