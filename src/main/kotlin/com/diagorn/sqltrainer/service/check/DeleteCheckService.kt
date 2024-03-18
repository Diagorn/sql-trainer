package com.diagorn.sqltrainer.service.check

import com.diagorn.sqltrainer.exception.BadSqlException
import com.diagorn.sqltrainer.exception.ComparisonFailedException
import com.diagorn.sqltrainer.exception.UnknownException
import com.diagorn.sqltrainer.model.mongo.Task
import com.diagorn.sqltrainer.service.resultCompare.RawResultSetExtractor
import com.diagorn.sqltrainer.service.resultCompare.ResultSetComparisonService
import com.diagorn.sqltrainer.utils.validDelete
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.stereotype.Service
import java.sql.ResultSet

/**
 * Сервис проверки запросов на удаление данных
 *
 * @author Diagorn
 */
@Service
class DeleteCheckService(
    private val jdbcTemplate: JdbcTemplate,
    rsExtractor: RawResultSetExtractor,
    private val resultSetComparisonService: ResultSetComparisonService,
    transactionManager: DataSourceTransactionManager
): AbstractSqlCheckService(transactionManager, jdbcTemplate, rsExtractor) {
    override fun validate(sql: String, task: Task) {
        // Проверяем на валидность
        if (!sql.validDelete()) {
            throw BadSqlException(WRONG_SQL)
        }

        if (task.sqlQuery.isEmpty()) {
            throw BadSqlException(FAILED_TASK_SQL)
        }
    }

    override fun compareEquality(userRs: ResultSet?, taskRs: ResultSet?, task: Task): Boolean {
        if (userRs == null || taskRs == null || task.modifiedTableName == null) {
            throw ComparisonFailedException(COMPARISON_FAILED)
        }
        return resultSetComparisonService.compareUpdate(userRs, taskRs, task.modifiedTableName)
    }

    override fun getQueryResults(sql: String, task: Task): ResultSet? {
        if (task.modifiedTableName == null) {
            throw UnknownException(SOMETHING_WENT_WRONG)
        }

        return selectFromTable(task.modifiedTableName)
    }

    override fun doExecuteSql(sql: String) {
        jdbcTemplate.execute(sql)
    }
}