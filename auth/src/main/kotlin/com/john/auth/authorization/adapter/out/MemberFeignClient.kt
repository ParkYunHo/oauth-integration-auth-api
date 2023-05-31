package com.john.auth.authorization.adapter.out

import com.john.auth.authorization.adapter.out.dto.LogoutResponse
import com.john.auth.config.OpenFeignConfig
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "MemberFeignClient", url = "http://localhost:8080", configuration = [OpenFeignConfig::class])
interface MemberFeignClient {

    @PostMapping(value = ["/api/logout"])
    fun logout(
        @RequestParam userId: String,
        // 에러케이스 TEST 용도
        @RequestParam isError: Boolean = false,
        //
    ): LogoutResponse
}