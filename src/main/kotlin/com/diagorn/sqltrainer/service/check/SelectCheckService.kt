package com.diagorn.sqltrainer.service.check

import com.diagorn.sqltrainer.exception.BadSqlException
import com.diagorn.sqltrainer.exception.ComparisonFailedException
import com.diagorn.sqltrainer.model.common.Row
import com.diagorn.sqltrainer.model.mongo.Task
import com.diagorn.sqltrainer.model.sql.DmlCommandsEnum
import com.diagorn.sqltrainer.service.resultCompare.RowResultSetExtractor
import com.diagorn.sqltrainer.service.resultCompare.ResultSetComparisonService
import com.diagorn.sqltrainer.utils.validSelect
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.stereotype.Service
import java.sql.ResultSet

/**
 * Сервис проверки запросов на получение данных
 *
 * @author Diagorn
 */
@Service
class SelectCheckService(
    private val jdbcTemplate: JdbcTemplate,
    private val rsExtractor: RowResultSetExtractor,
    private val resultSetComparisonService: ResultSetComparisonService,
    transactionManager: DataSourceTransactionManager
): AbstractSqlCheckService(transactionManager, jdbcTemplate, rsExtractor) {
    override fun validate(sql: String, task: Task) {
        // Проверяем на валидность
        if (!sql.validSelect()) {
            throw BadSqlException(WRONG_SQL)
        }

        if (task.sqlQuery.isEmpty()) {
            throw BadSqlException(FAILED_TASK_SQL)
        }
    }

    override fun compareEquality(userRs: List<Row>, taskRs: List<Row>, task: Task): Boolean {
        if (!Row.compareEmpty(userRs, taskRs)) {
            throw ComparisonFailedException(COMPARISON_FAILED)
        }
        return resultSetComparisonService.compareSelect(userRs, taskRs, task.orderImportant ?: false)
    }

    override fun getQueryResults(sql: String, task: Task): List<Row> {
        return jdbcTemplate.query(sql, rsExtractor) ?: listOf() // todo мб вставить пустой ряд с названиями колонок?
    }

    override fun doExecuteSql(sql: String) {}
    override fun getDmlCommand(): DmlCommandsEnum = DmlCommandsEnum.SELECT
}