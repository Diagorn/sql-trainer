package com.diagorn.sqltrainer.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
class PostgresSetupConfig(
    val postgresMainConnectionProperties: PostgresMainConnectionProperties,
) {

    @Bean
    fun postgresMainDataSource(): DataSource {
        val config = HikariConfig().apply {
            jdbcUrl = postgresMainConnectionProperties.jdbcUrl
            username = postgresMainConnectionProperties.username
            password = postgresMainConnectionProperties.password
        }
        return HikariDataSource(config)
    }

    @Bean
    fun postgresMainJdbcTemplate(): JdbcTemplate = JdbcTemplate(postgresMainDataSource())

}