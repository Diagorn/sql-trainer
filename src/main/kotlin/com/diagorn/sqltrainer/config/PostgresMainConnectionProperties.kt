package com.diagorn.sqltrainer.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.data.postgres")
class PostgresMainConnectionProperties(
    val jdbcUrl: String,
    val username: String,
    val password: String,
)

@ConfigurationProperties(prefix = "spring.data.postgres-test")
class PostgresConnectionProperties(
    val jdbcUrl: String,
    val username: String,
    val password: String,
)