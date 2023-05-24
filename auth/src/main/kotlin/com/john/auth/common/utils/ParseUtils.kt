package com.john.auth.common.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeInput
import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeRedirectInput
import com.john.auth.client.application.dto.RegisteredClientEntity
import com.john.auth.client.domain.RegisteredClient
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import java.net.URLEncoder

/**
 * @author yoonho
 * @since 2023.05.13
 */
object ParseUtils {
    lateinit var objectMapperStatic: ObjectMapper

    /**
     * ObjectMapper DI
     *
     * @param objectMapper [ObjectMapper]
     * @author yoonho
     * @since 2023.05.15
     */
    fun setObjectMapper(objectMapper: ObjectMapper) {
        this.objectMapperStatic = objectMapper
    }

    /**
     * Domain을 Entity로 매핑 (Domain > Entity)
     *
     * @param domain [RegisteredClient]
     * @return [RegisteredClientEntity]
     * @author yoonho
     * @since 2023.05.15
     */
    fun toEntity(domain: RegisteredClient): RegisteredClientEntity =
        objectMapperStatic.convertValue(domain, object: TypeReference<RegisteredClientEntity>() {})

    /**
     * Entity을 Domain로 매핑 (Entity > Domain)
     *
     * @param entity [RegisteredClientEntity]
     * @return [RegisteredClient]
     * @author yoonho
     * @since 2023.05.15
     */
    fun toDomain(entity: RegisteredClientEntity): RegisteredClient =
        objectMapperStatic.convertValue(entity, object: TypeReference<RegisteredClient>() {})

    /**
     * 로그인페이지로 리다이렉트시 전달할 매개변수 설정
     *
     * @param input [AuthorizationCodeInput]
     * @return [String]
     * @author yoonho
     * @since 2023.05.15
     */
    fun getState(input: AuthorizationCodeInput): String =
        URLEncoder.encode("redirect_uri=${input.redirect_uri}&scope=${input.scope}&state=${input.state}&client_id=${input.client_id}&client_secret=${input.client_secret}", "UTF-8")

    /**
     * 인가코드 파라미터 설정
     *
     * @param input [AuthorizationCodeRedirectInput]
     * @return [String]
     * @author yoonho
     * @since 2023.05.19
     */
    fun getAuthorizationParameter(input: AuthorizationCodeRedirectInput): String =
        "redirect_uri=${input.redirect_uri}&scope=${input.scope}&state=${input.state}&client_id=${input.client_id}&client_secret=${input.client_secret}&userId=${input.userId}"

    /**
     * Header에서 AccessToken 추출
     *
     * @param request [HttpServletRequest]
     * @return [String]
     * @author yoonho
     * @since 2023.05.24
     */
    fun getHeaderBearerToken(request: HttpServletRequest): String {
        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION)
        val bearerPrefix = "Bearer "
        if(bearerPrefix in authorization) {
            return authorization.substringAfter(bearerPrefix)
        }

        return ""
    }
}