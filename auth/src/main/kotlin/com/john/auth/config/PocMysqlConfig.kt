package com.john.auth.config

import com.john.auth.common.repository.PocRepository
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaDialect
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.vault.annotation.VaultPropertySource
import javax.sql.DataSource

/**
 * @author yoonho
 * @since 2023.06.12
 */
@Configuration
@EnableTransactionManagement
@VaultPropertySource("\${vault.secret.appconfig}")
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
    //  - Vault 연동시 appRole 방식으로 할 것

    @Bean
    fun pocDataSource(): DataSource {
        val hikariConfig = HikariConfig().apply {
            driverClassName = "com.mysql.cj.jdbc.Driver"
            jdbcUrl = env.getProperty("mysql.poc.url")
            username = env.getProperty("mysql.poc.username")
            password = env.getProperty("mysql.poc.password")
            connectionTimeout = env.getProperty("spring.datasource.hikari.connection-timeout")!!.toLong()
            maximumPoolSize = env.getProperty("spring.datasource.hikari.maximum-pool-size")!!.toInt()
            minimumIdle = env.getProperty("spring.datasource.hikari.minimum-idle")!!.toInt()
        }
        return HikariDataSource(hikariConfig)
    }

    @Bean
    fun pocEntityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        return LocalContainerEntityManagerFactoryBean().apply {
            dataSource = pocDataSource()
            jpaVendorAdapter = HibernateJpaVendorAdapter()
            jpaDialect = HibernateJpaDialect()
            setPackagesToScan("com.john.auth")
            setJpaPropertyMap(
                    mapOf(
                            "hibernate.physical_naming_strategy" to env.getProperty("hibernate.physical_naming_strategy"),
                            "hibernate.show_sql" to env.getProperty("hibernate.show-sql"),
                            "hibernate.format_sql" to env.getProperty("hibernate.format_sql")
                    )
            )
        }
    }

    @Bean
    fun pocTransactionManager(): JpaTransactionManager {
        return JpaTransactionManager().apply { entityManagerFactory = pocEntityManagerFactory().`object` }
    }
}