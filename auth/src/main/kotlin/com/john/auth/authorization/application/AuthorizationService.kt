package com.john.auth.authorization.application

import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeInput
import com.john.auth.authorization.application.port.`in`.AuthorizationCodeCheckUseCase
import com.john.auth.authorization.application.port.out.FindPort
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder

/**
 * @author yoonho
 * @since 2023.05.13
 */
@Service
class AuthorizationService(
    private val findPort: FindPort
): AuthorizationCodeCheckUseCase {

    override fun check(input: AuthorizationCodeInput): String {
        findPort.checkClient(input = input)

        return UriComponentsBuilder
            .fromHttpUrl("http://localhost:9000/oauth/consent")
            .queryParam("redirect_uri", input.redirect_uri)
            .queryParam("state", input.state)
            .toUriString()
    }

}