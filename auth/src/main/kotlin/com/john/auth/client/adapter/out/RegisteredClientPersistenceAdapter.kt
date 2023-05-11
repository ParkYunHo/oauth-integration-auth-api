package com.john.auth.client.adapter.out

import org.springframework.stereotype.Repository

/**
 * @author yoonho
 * @since 2023.05.11
 */
@Repository
class RegisteredClientPersistenceAdapter(
    private val registeredClientRepository: RegisteredClientRepository
) {

}