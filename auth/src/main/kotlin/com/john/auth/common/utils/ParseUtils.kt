package com.john.auth.common.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.john.auth.authorization.adapter.`in`.web.dto.AuthorizationCodeRedirectInput
import com.john.auth.client.application.dto.RegisteredClientEntity
import com.john.auth.client.domain.RegisteredClient
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
     * @param redirectUrl [String]
     * @param scope [String]
     * @param state [String]
     * @return [String]
     * @author yoonho
     * @since 2023.05.15
     */
    fun getState(redirectUrl: String, scope: String, state: String, clientId: String): String =
        URLEncoder.encode("redirect_uri=$redirectUrl&scope=$scope&state=$state&client_id=$clientId", "UTF-8")

    /**
     * 인가코드 파라미터 설정
     *
     * @param input [AuthorizationCodeRedirectInput]
     * @return [String]
     * @author yoonho
     * @since 2023.05.19
     */
    fun getAuthorizationParameter(input: AuthorizationCodeRedirectInput): String =
        "redirect_uri=${input.redirect_uri}&scope=${input.scope}&state=${input.state}&client_id=${input.client_id}&userId=${input.userId}"
}