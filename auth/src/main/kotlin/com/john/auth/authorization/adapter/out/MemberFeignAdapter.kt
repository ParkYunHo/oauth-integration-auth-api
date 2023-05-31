package com.john.auth.authorization.adapter.out

import com.john.auth.authorization.application.port.out.RestCallPort
import com.john.auth.common.constants.RestCallClientType
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

/**
 * @author yoonho
 * @since 2023.05.28
 */
@Repository
class MemberFeignAdapter(
    private val memberFeignClient: MemberFeignClient,
): RestCallPort {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * ResourceServer 로그아웃API 호출 (OpenFeign)
     *
     * @param userId [String]
     * @author yoonho
     * @since 2023.05.28
     */
    override fun restCallLogout(userId: String) {
        val response = memberFeignClient.logout(userId = userId, isError = true)
        log.info(" >>> [restCallLogout] response: $response")
    }

    /**
     * RestCall 통신Client 지원여부 조회
     *
     * @param type [String]
     * @return [Boolean]
     * @author yoonho
     * @since 2023.05.28
     */
    override fun support(type: String): Boolean =
        type == RestCallClientType.OPENFEIGN.code
}