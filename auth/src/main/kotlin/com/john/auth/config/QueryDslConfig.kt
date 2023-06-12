package com.john.auth.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author yoonho
 * @since 2023.06.12
 */
@Configuration
class QueryDslConfig {
    @PersistenceContext(unitName = "pocEntityManagerFactory")
    lateinit var pocEntityManager: EntityManager

    @Bean
    fun pocQueryFactory() = JPAQueryFactory(pocEntityManager)
}