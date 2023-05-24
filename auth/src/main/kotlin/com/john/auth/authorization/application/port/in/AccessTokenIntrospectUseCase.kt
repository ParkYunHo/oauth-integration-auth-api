package com.john.auth.authorization.application.port.`in`

import com.john.auth.authorization.application.dto.IntrospectDto

/**
 * @author yoonho
 * @since 2023.05.24
 */
interface AccessTokenIntrospectUseCase {

    /**
     * 토큰검사
     *
     * @param accessToken [String]
     * @return [IntrospectDto]
     * @author yoonho
     * @since 2023.05.24
     */
    fun introspect(accessToken: String): IntrospectDto
}