package com.john.res.account.application

import com.john.res.account.adapter.`in`.web.dto.AuthInput
import com.john.res.account.application.port.`in`.LoginUseCase
import com.john.res.account.application.port.`in`.RegisterUseCase
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * @author yoonho
 * @since 2023.05.09
 */
@Service
class AccountService(

): LoginUseCase, RegisterUseCase {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun login(input: AuthInput): Boolean {
        TODO("Not yet implemented")
    }
}