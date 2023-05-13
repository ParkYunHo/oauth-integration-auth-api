package com.john.auth.common.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.john.auth.client.application.dto.RegisteredClientEntity
import com.john.auth.client.domain.RegisteredClient

/**
 * @author yoonho
 * @since 2023.05.13
 */
object ParseUtils {
    lateinit var objectMapperStatic: ObjectMapper

    fun setObjectMapper(objectMapper: ObjectMapper) {
        this.objectMapperStatic = objectMapper
    }

    fun toEntity(domain: RegisteredClient): RegisteredClientEntity =
        objectMapperStatic.convertValue(domain, object: TypeReference<RegisteredClientEntity>() {})

    fun toDomain(entity: RegisteredClientEntity): RegisteredClient =
        objectMapperStatic.convertValue(entity, object: TypeReference<RegisteredClient>() {})
}