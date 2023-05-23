package com.john.auth.authorization.application.port.out

import com.john.auth.authorization.application.dto.AuthorizationCodeDto
import com.john.auth.authorization.application.dto.IssueTokenDto
import com.john.auth.authorization.application.dto.TokenInfo
import com.john.auth.authorization.domain.Authorization

/**
 * @author yoonho
 * @since 2023.05.19
 */
interface SavePort {

    /**
     * 인가코드 발급
     *
     * @param input [AuthorizationCodeDto]
     * @author yoonho
     * @since 2023.05.19
     */
    fun authorizationCodeRegister(input: AuthorizationCodeDto)

    /**
     * 토큰발급
     *
     * @param authorization [Authorization]
     * @author yoonho
     * @since 2023.05.23
     */
    fun tokenInfoRegister(authorization: Authorization)
}