package com.diagorn.sqltrainer.service

import com.diagorn.sqltrainer.config.PostgresConnectionProperties
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.io.Writer
import javax.sql.DataSource

/**
 * Сервис подготовки базы данных postgres к использованию с нуля
 *
 * @param mainJdbcTemplate - jdbcTemplate на основной базе данных. Используется для удаления и воссоздания БД
 * @param connectionProperties - параметры подключения к тестовой базе данных
 *
 * @author Diagorn
 */
@Service
class SetupPostgresService(
    @Qualifier("postgresMainJdbcTemplate") private val mainJdbcTemplate: JdbcTemplate,
    private val connectionProperties: PostgresConnectionProperties,
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    /**
     * Удаляет базу данных и создаёт её заново
     */
    fun recreateDatabase() {
        logger.info("Удаление и воссоздание базы данных")

        val databaseName = connectionProperties.jdbcUrl.substringAfterLast('/')
        mainJdbcTemplate.execute("$DROP_DATABASE $databaseName")
        mainJdbcTemplate.execute("$CREATE_DATABASE $databaseName")

        logger.info("Удаление и воссоздание базы данных прошло успешно")
    }

    /**
     * Прогоняет миграции с нуля на созданной БД
     *
     * @param targetDataSource - датасорс подключения к тестовой базе данных
     */
    fun runMigrations(targetDataSource: DataSource) {
        logger.info("Прогон миграций")

        val database = DatabaseFactory
            .getInstance()
            .findCorrectDatabaseImplementation(JdbcConnection(targetDataSource.connection))

        val liquibase = Liquibase(CHANGELOG_FILE, ClassLoaderResourceAccessor(), database)

        liquibase.update("", Writer.nullWriter())

        logger.info("Прогон миграций прошёл успешно")
    }

    companion object {
        private const val DROP_DATABASE = "drop database if exists "
        private const val CREATE_DATABASE = "create database "
        private const val CHANGELOG_FILE = "db/changelog/db.changelog-master.yaml"
    }
}