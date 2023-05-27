package com.john.auth.authorization.application.port.`in`

import com.john.auth.authorization.application.dto.LogoutDto

/**
 * @author yoonho
 * @since 2023.05.27
 */
interface LogoutUseCase {

    /**
     * 로그아웃
     *
     * @param userId [String]
     * @param accessToken [String]
     * @return [LogoutDto]
     * @author yoonho
     * @since 2023.05.27
     */
    fun logout(userId: String, accessToken: String): LogoutDto
}