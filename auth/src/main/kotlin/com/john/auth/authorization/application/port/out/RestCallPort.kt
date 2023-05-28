package com.john.auth.authorization.application.port.out

/**
 * @author yoonho
 * @since 2023.05.28
 */
interface RestCallPort {

    /**
     * ResourceServer 로그아웃API 호출 (HttpClient)
     *
     * @param userId [String]
     * @author yoonho
     * @since 2023.05.28
     */
    fun restCallLogoutHttpClient(userId: String)

}