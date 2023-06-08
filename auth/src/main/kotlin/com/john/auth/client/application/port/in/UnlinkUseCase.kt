package com.john.auth.client.application.port.`in`

/**
 * @author yoonho
 * @since 2023.05.31
 */
interface UnlinkUseCase {

    /**
     * Client Unlink
     *
     * @param clientId [Long]
     * @param accessToken [String]
     * @author yoonho
     * @since 2023.05.31
     */
    fun unlink(clientId: Long, accessToken: String)
}