package com.john.res.account.adapter.out.persistence

import com.john.res.account.adapter.`in`.web.dto.AuthInput
import com.john.res.account.adapter.`in`.web.dto.RegisterInput
import com.john.res.account.application.port.out.FindPort
import com.john.res.account.application.port.out.SavePort
import com.john.res.account.domain.Account
import com.john.res.common.exception.NotRegisteredUserException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.05.10
 */
@Repository
class AccountPersistanceAdapter(
    private val accountRepository: AccountRepository
): FindPort, SavePort {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 로그인 프로세스
     *
     * @param input [AuthInput]
     * @return [String]
     * @author yoonho
     * @since 2023.05.10
     */
    override fun findUserIdAndPassword(input: AuthInput): String {
        log.info(" >>> [findUserIdAndPassword] userId: ${input.userId}, password: ${input.password}")

        return try {
            "userId=${accountRepository.findByUserIdAndPassword(userId = input.userId, password = input.password)
                .orElseThrow { throw NotRegisteredUserException("등록된 사용자가 아닙니다.") }
                .userId!!}"
        }catch (e: Exception) {
            log.error(" >>> [findUserIdAndPassword] Exception occurs - message: ${e.message}")
            "code=401&error_description=NotRegisteredUser"
        }
    }

    /**
     * 회원가입 프로세스
     *
     * @param input [RegisterInput]
     * @return [Boolean]
     * @author yoonho
     * @since 2023.05.10
     */
    override fun saveUserInfo(input: RegisterInput): Boolean {
        log.info(" >>> [saveUserInfo] input: $input")

        accountRepository.save(
            Account(
                userId = input.userId,
                password = input.password,
                nickName = input.nickname,
                email = input.email,
                birthday = input.birthday,
                gender = input.gender,
                korName = input.korName,
                updatedAt = LocalDateTime.now()
            )
        )

        return true
    }
}