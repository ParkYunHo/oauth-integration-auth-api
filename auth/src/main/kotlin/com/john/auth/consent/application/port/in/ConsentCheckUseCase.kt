package com.john.auth.consent.application.port.`in`

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeRedirectInput

/**
 * @author yoonho
 * @since 2023.05.19
 */
interface ConsentCheckUseCase {
    fun checkConsent(input: AuthorizationCodeRedirectInput): Boolean
}