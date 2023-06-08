package com.john.auth.client.application.port.out

/**
 * @author yoonho
 * @since 2023.05.13
 */
interface DeletePort {

    /**
     * Client 삭제
     *
     * @param clientId [Long]
     * @author yoonho
     * @since 2023.05.13
     */
    fun delete(clientId: Long)


}