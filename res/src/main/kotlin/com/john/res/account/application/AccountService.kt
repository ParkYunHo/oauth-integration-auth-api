package com.john.res.account.application

import com.john.res.account.adapter.`in`.web.dto.AuthInput
import com.john.res.account.adapter.`in`.web.dto.RegisterInput
import com.john.res.account.application.port.`in`.LoginUseCase
import com.john.res.account.application.port.`in`.RegisterUseCase
import com.john.res.account.application.port.out.FindPort
import com.john.res.account.application.port.out.SavePort
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * @author yoonho
 * @since 2023.05.09
 */
@Service
class AccountService(
    private val findPort: FindPort,
    private val savePort: SavePort
): LoginUseCase, RegisterUseCase {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun login(input: AuthInput): String {
        val userId = findPort.findUserIdAndPassword(input)
        return "${input.redirectUri}&$userId"
    }

    override fun register(input: RegisterInput): Boolean =
        savePort.saveUserInfo(input)
}