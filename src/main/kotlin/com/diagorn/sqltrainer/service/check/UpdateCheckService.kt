package com.diagorn.sqltrainer.service.check

import com.diagorn.sqltrainer.exception.BadSqlException
import com.diagorn.sqltrainer.exception.ComparisonFailedException
import com.diagorn.sqltrainer.exception.UnknownException
import com.diagorn.sqltrainer.model.common.Row
import com.diagorn.sqltrainer.model.mongo.Task
import com.diagorn.sqltrainer.model.sql.DmlCommandsEnum
import com.diagorn.sqltrainer.service.resultCompare.RowResultSetExtractor
import com.diagorn.sqltrainer.service.resultCompare.ResultSetComparisonService
import com.diagorn.sqltrainer.utils.validUpdate
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.stereotype.Service
import java.sql.ResultSet

/**
 * Сервис проверки запросов на обновление данных
 *
 * @author Diagorn
 */
@Service
class UpdateCheckService(
    private val jdbcTemplate: JdbcTemplate,
    rsExtractor: RowResultSetExtractor,
    private val resultSetComparisonService: ResultSetComparisonService,
    transactionManager: DataSourceTransactionManager
): AbstractSqlCheckService(transactionManager, jdbcTemplate, rsExtractor) {
    override fun validate(sql: String, task: Task) {
        // Проверяем на валидность
        if (!sql.validUpdate()) {
            throw BadSqlException(WRONG_SQL)
        }

        if (task.sqlQuery.isEmpty()) {
            throw BadSqlException(FAILED_TASK_SQL)
        }
    }

    override fun compareEquality(userRs: List<Row>, taskRs: List<Row>, task: Task): Boolean {
        if (!Row.compareEmpty(userRs, taskRs) || task.modifiedTableName == null) {
            throw ComparisonFailedException(COMPARISON_FAILED)
        }
        return resultSetComparisonService.compareUpdate(userRs, taskRs, task.modifiedTableName)
    }

    override fun getQueryResults(sql: String, task: Task): List<Row> {
        if (task.modifiedTableName == null) {
            throw UnknownException(SOMETHING_WENT_WRONG)
        }

        return selectFromTable(task.modifiedTableName)
    }

    override fun doExecuteSql(sql: String) {
        jdbcTemplate.execute(sql)
    }

    override fun getDmlCommand(): DmlCommandsEnum = DmlCommandsEnum.UPDATE
}