package com.john.auth.client.adapter.`in`.web

import com.john.auth.client.adapter.`in`.web.dto.ClientReIssueInput
import com.john.auth.client.adapter.`in`.web.dto.ClientRegistInput
import com.john.auth.client.adapter.`in`.web.dto.ClientUpdateInput
import com.john.auth.client.application.dto.RegisteredClientEntity
import com.john.auth.client.application.port.`in`.DeleteUseCase
import com.john.auth.client.application.port.`in`.RegistUseCase
import com.john.auth.client.application.port.`in`.UpdateUseCase
import com.john.auth.common.BaseResponse
import com.john.auth.common.constants.ReIssueType
import com.john.auth.common.exception.NotFoundException
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
    @PostMapping("/api/client")
    fun regist(@ModelAttribute @Validated input: ClientRegistInput): BaseResponse<RegisteredClientEntity> {
        log.info(" >>> [regist] input: $input")

        val result = registUseCase.regist(input = input)
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
    @PatchMapping("/api/client/{clientId}")
    fun update(@ModelAttribute @Validated input: ClientUpdateInput): BaseResponse<RegisteredClientEntity> {
        log.info(" >>> [update] input: $input")

        val result = updateUseCase.update(input = input)
        return BaseResponse.Success(data = result)
    }

    /**
     * Client Key정보 재발급
     *
     * @param input [ClientReIssueInput]
     * @return [BaseResponse]<[RegisteredClientEntity]>
     * @author yoonho
     * @since 2023.05.13
     */
    @PutMapping("/api/client/{clientId}")
    fun reIssue(@ModelAttribute @Validated input: ClientReIssueInput): BaseResponse<RegisteredClientEntity> {
        log.info(" >>> [reIssue] input: $input")

        if(ReIssueType.invalidTarget(input.target)) {
            throw NotFoundException("업데이트 항목을 찾을 수 없습니다.")
        }

        val result = updateUseCase.reIssue(input = input)
        return BaseResponse.Success(data = result)
    }

    /**
     * Client 삭제
     *
     * @param input [Long]
     * @return [BaseResponse]<[Void]>
     * @author yoonho
     * @since 2023.05.13
     */
    @DeleteMapping("/api/client/{clientId}")
    fun delete(@PathVariable("clientId") input: Long): BaseResponse<Void> {
        log.info(" >>> [delete] input: $input")

        deleteUseCase.delete(input = input)
        return BaseResponse.SuccessNoContent()
    }

}