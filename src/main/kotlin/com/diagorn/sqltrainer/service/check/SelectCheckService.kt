package com.diagorn.sqltrainer.service.check

import com.diagorn.sqltrainer.exception.BadSqlException
import com.diagorn.sqltrainer.exception.ComparisonFailedException
import com.diagorn.sqltrainer.model.mongo.Task
import com.diagorn.sqltrainer.model.sql.DmlCommandsEnum
import com.diagorn.sqltrainer.service.resultCompare.RawResultSetExtractor
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
    private val rsExtractor: RawResultSetExtractor,
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

    override fun compareEquality(userRs: ResultSet?, taskRs: ResultSet?, task: Task): Boolean {
        if (userRs == null || taskRs == null) {
            throw ComparisonFailedException(COMPARISON_FAILED)
        }
        return resultSetComparisonService.compareSelect(userRs, taskRs, task.orderImportant ?: false)
    }

    override fun getQueryResults(sql: String, task: Task): ResultSet? {
        return jdbcTemplate.query(sql, rsExtractor)
    }

    override fun doExecuteSql(sql: String) {}
    override fun getDmlCommand(): DmlCommandsEnum = DmlCommandsEnum.SELECT
}