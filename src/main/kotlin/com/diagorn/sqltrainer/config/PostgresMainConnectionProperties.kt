package com.diagorn.sqltrainer.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Параметры подключения к основной базе данных
 *
 * @param jdbcUrl - урл подключения к бд
 * @param username - логин
 * @param password - пароль
 *
 * @author Diagorn
 */
@ConfigurationProperties(prefix = "spring.data.postgres")
class PostgresMainConnectionProperties(
    val jdbcUrl: String,
    val username: String,
    val password: String,
)

/**
 * Параметры подключения к тестовой базе данных
 *
 * @param jdbcUrl - урл подключения к бд
 * @param username - логин
 * @param password - пароль
 *
 * @author Diagorn
 */
@ConfigurationProperties(prefix = "spring.data.postgres-test")
class PostgresConnectionProperties(
    val jdbcUrl: String,
    val username: String,
    val password: String,
)