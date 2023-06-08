package com.john.auth.client.application.port.`in`

/**
 * @author yoonho
 * @since 2023.05.13
 */
interface DeleteUseCase {

    /**
     * Client 삭제
     *
     * @param clientId [Long]
     * @author yoonho
     * @since 2023.05.13
     */
    fun delete(clientId: Long)
}