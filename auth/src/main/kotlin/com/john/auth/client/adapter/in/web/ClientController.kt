package com.john.auth.client.adapter.`in`.web

import com.john.auth.client.adapter.`in`.web.dto.ClientRegistInput
import com.john.auth.client.adapter.`in`.web.dto.ClientUpdateInput
import com.john.auth.client.application.dto.RegisteredClientEntity
import com.john.auth.client.application.port.`in`.DeleteUseCase
import com.john.auth.client.application.port.`in`.RegistUseCase
import com.john.auth.client.application.port.`in`.UpdateUseCase
import com.john.auth.common.BaseResponse
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * @author yoonho
 * @since 2023.05.11
 */
@RestController
class ClientController(
    private val registUseCase: RegistUseCase,
    private val updateUseCase: UpdateUseCase,
    private val deleteUseCase: DeleteUseCase
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

    /**
     * Client 정보수정
     *
     * @param input [ClientUpdateInput]
     * @return [BaseResponse]<[RegisteredClientEntity]>
     * @author yoonho
     * @since 2023.05.13
     */
    @PatchMapping("/api/client/update/{clientId}")
    fun update(@ModelAttribute @Validated input: ClientUpdateInput): BaseResponse<RegisteredClientEntity> {
        log.info(" >>> [update] input: $input")

        val result = updateUseCase.update(input)
        return BaseResponse.Success(data = result)
    }

    @DeleteMapping("/api/client/delete/{clientId}")
    fun delete(@PathVariable("clientId") input: Long): BaseResponse<Void> {
        log.info(" >>> [delete] input: $input")

        deleteUseCase.delete(input)
        return BaseResponse.SuccessNoContent()
    }

}