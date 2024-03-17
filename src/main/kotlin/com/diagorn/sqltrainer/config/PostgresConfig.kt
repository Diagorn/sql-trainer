package com.diagorn.sqltrainer.config

import com.diagorn.sqltrainer.service.SetupPostgresService
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
class PostgresConfig(
    private val setupPostgresService: SetupPostgresService,
    private val postgresConnectionProperties: PostgresConnectionProperties
) {

    @Bean
    fun postgresTestDataSource(): DataSource {
        // Пересоздаём базу с нуля
        setupPostgresService.recreateDatabase()

        // Создаём датасорс на пересозданной базе
        val resultDataSource = HikariDataSource(HikariConfig().apply {
            jdbcUrl = postgresConnectionProperties.jdbcUrl
            username = postgresConnectionProperties.username
            password = postgresConnectionProperties.password
            isAutoCommit = false
        })

        // Вызываем прогон миграций на пересозданной базе
        setupPostgresService.runMigrations(resultDataSource)

        return resultDataSource
    }

    @Bean
    @Primary
    fun postgresJdbcTemplate(): JdbcTemplate = JdbcTemplate(postgresTestDataSource())
}