package com.john.auth.authorization.application.port.out

/**
 * @author yoonho
 * @since 2023.05.28
 */
interface RestCallPort {

    /**
     * ResourceServer 로그아웃API 호출
     *
     * @param userId [String]
     * @author yoonho
     * @since 2023.05.28
     */
    fun restCallLogout(userId: String)

    /**
     * RestCall 통신Client 지원여부 조회
     *
     * @param type [String]
     * @return [Boolean]
     * @author yoonho
     * @since 2023.05.28
     */
    fun support(type: String): Boolean
}