package com.john.auth.config

import com.john.auth.common.repository.PocRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

/**
 * @author yoonho
 * @since 2023.06.08
 */
@Configuration
@EnableTransactionManagement
//@VaultPropertySource("\${vault.secret.appconfig}")
@EnableJpaRepositories(
    entityManagerFactoryRef = "pocEntityManagerFactory",
    transactionManagerRef = "pocTransactionManager",
    basePackageClasses = [PocRepository::class]
)
class PocMysqlConfig(
    private val env: Environment
) {

    // TODO:
    //  - QueryDSL 설정
    //  - Docker로 DB 2개 띄우고 따로 설정해볼것 (h2말고, MySQL인스턴스 port번호 다르게 2개 띄울것)
    //  - Vault Docker 띄우고 Vault 설정

    @Bean
    fun pocDataSource(): DataSource {

    }

    @Bean
    fun pocEntityManagerFactory(): LocalContainerEntityManagerFactoryBean {

    }

    @Bean
    fun pocTransactionManager(): JpaTransactionManager {

    }
}