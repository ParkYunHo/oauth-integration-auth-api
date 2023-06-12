package com.john.auth.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.vault.authentication.ClientAuthentication
import org.springframework.vault.authentication.TokenAuthentication
import org.springframework.vault.client.VaultEndpoint
import org.springframework.vault.config.AbstractVaultConfiguration
import java.net.URI

/**
 * @author yoonho
 * @since 2023.06.12
 */
@Primary
@Configuration
class VaultConfig: AbstractVaultConfiguration() {

    override fun vaultEndpoint(): VaultEndpoint {
        val vaultUri = environment.getProperty("vault.uri") ?: throw NullPointerException("vault uri가 없습니다.")
        return VaultEndpoint.from(URI(vaultUri))
    }

    override fun clientAuthentication(): ClientAuthentication {
        return TokenAuthentication(environment.getProperty("vault.token") ?: throw NullPointerException("vault token이 없습니다."))
    }
}