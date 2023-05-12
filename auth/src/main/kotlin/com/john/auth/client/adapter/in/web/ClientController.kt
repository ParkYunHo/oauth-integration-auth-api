package com.john.auth.client.adapter.`in`.web

import com.john.auth.client.adapter.`in`.web.dto.ClientRegistInput
import com.john.auth.client.application.dto.RegisteredClientEntity
import com.john.auth.client.application.port.`in`.RegistUseCase
import com.john.auth.common.BaseResponse
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author yoonho
 * @since 2023.05.11
 */
@RestController
class ClientController(
    private val registUseCase: RegistUseCase
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * Client 등록
     *
     * @param input [ClientRegistInput]
     * @return [BaseResponse]<[Void]>
     * @author yoonho
     * @since 2023.05.12
     */
    @PostMapping("/api/client/regist")
    fun regist(@ModelAttribute @Validated input: ClientRegistInput): BaseResponse<RegisteredClientEntity> {
        log.info(" >>> [regist] input: $input")

        val result = registUseCase.regist(input)
        return BaseResponse.Success(data = result)
    }

}