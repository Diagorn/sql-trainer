package com.diagorn.sqltrainer.service.check

import com.diagorn.sqltrainer.exception.BadSqlException
import com.diagorn.sqltrainer.model.common.Row
import com.diagorn.sqltrainer.model.mongo.Task
import com.diagorn.sqltrainer.model.sql.DmlCommandsEnum
import com.diagorn.sqltrainer.rest.dto.TaskResult
import com.diagorn.sqltrainer.service.resultCompare.RowResultSetExtractor
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.DefaultTransactionDefinition
import java.sql.ResultSet
import kotlin.properties.Delegates

/**
 * Класс, прогоняющий sql-запросы и сравнивающий их между собой
 *
 * @param transactionManager - менеджер транзакций, который откатывает результаты прогона
 * @param jdbcTemplate - jdbcTemplate, через который идут запросы
 * @param rsExtractor - преобразователь результатов запроса в ResultSet
 *
 * @author Diagorn
 */
abstract class AbstractSqlCheckService(
    private val transactionManager: DataSourceTransactionManager,
    private val jdbcTemplate: JdbcTemplate,
    private val rsExtractor: RowResultSetExtractor,
) {

    /**
     * Выполнить проверку
     *
     * @param sql - пользовательский запрос
     * @param task - задача, которую решает пользователь
     *
     * @return результат проверки с метриками
     */
    fun execute(sql: String, task: Task): TaskResult {

        validate(sql, task)

        val sqlExecutionTime: Long
        val taskSqlExecutionTime: Long
        val queryResults: List<Row>
        val taskQueryResults: List<Row>

        // Выполняем пользовательский SQL
        val sqlResult = doExecute(sql, task)
        queryResults = sqlResult.first
        sqlExecutionTime = sqlResult.second

        // Выполняем SQL из задания
        val taskSqlResult = doExecute(task.sqlQuery, task)
        taskQueryResults = taskSqlResult.first
        taskSqlExecutionTime = taskSqlResult.second

        // Рассчитываем равенство
        if (!compareEquality(
            userRs = queryResults,
            taskRs = taskQueryResults,
            task = task
        )) {
            throw BadSqlException(COMPARISON_FAILED)
        }

        // Рассчитываем разницу во времени исполнения в процентах
        val executionTimeDifference = countExecutionTimeDifference(sqlExecutionTime, taskSqlExecutionTime)

        // Рассчитываем разницу в длине
        val sqlLengthDifference = countSqlLengthDifference(sql, task.sqlQuery)

        return TaskResult(
            contentsEqual = true,
            executionTimeDifference = executionTimeDifference,
            contentLengthDifference = sqlLengthDifference
        )
    }

    private fun doExecute(
        sql: String,
        task: Task,
    ): Pair<List<Row>, Long> {
        val queryResults: List<Row>
        var sqlExecutionTime by Delegates.notNull<Long>()
        try {
            val transactionStatus = beginTransaction()

            val beforeExecutionTime = System.currentTimeMillis()
            doExecuteSql(sql)
            queryResults = getQueryResults(sql, task)
            val afterExecutionTime = System.currentTimeMillis()

            rollback(transactionStatus)

            sqlExecutionTime = afterExecutionTime - beforeExecutionTime
        } catch (e: Exception) {
            throw BadSqlException(FAILED_SQL)
        }
        return Pair(queryResults, sqlExecutionTime)
    }

    private fun countExecutionTimeDifference(sqlExecutionTime: Long, taskSqlExecutionTime: Long): Double {
        var executionTimeDifference = 100.0;
        if (sqlExecutionTime > taskSqlExecutionTime && sqlExecutionTime != 0L && taskSqlExecutionTime != 0L) {
            executionTimeDifference =
                100.0 - (sqlExecutionTime.toDouble() - taskSqlExecutionTime.toDouble()) / taskSqlExecutionTime * 100
        }
        return executionTimeDifference
    }

    private fun countSqlLengthDifference(sql: String, taskSql: String): Double {
        var sqlLengthDifference = 100.0
        if (sql.length > taskSql.length) {
            sqlLengthDifference =
                100.0 - (sql.length.toDouble() - taskSql.length.toDouble()) / taskSql.length * 100
        }
        return sqlLengthDifference
    }

    private fun beginTransaction(): TransactionStatus {
        val definition = DefaultTransactionDefinition()
            .apply { propagationBehavior = TransactionDefinition.PROPAGATION_REQUIRED }
        return transactionManager.getTransaction(definition)
    }

    private fun rollback(status: TransactionStatus) = transactionManager.rollback(status)

    /**
     * Провалидировать запрос. Выбрасывается ошибка, если запрос невалиден
     * @see BadSqlException
     *
     * @param sql - запрос, который нужно провалидировать
     * @param task - задача, к которой относится этот запрос
     */
    protected abstract fun validate(sql: String, task: Task)

    /**
     * Сравнить равенство двух ResultSet'ов
     *
     * @param userRs - результат, полученный в результате пользовательского запроса
     * @param taskRs - результат, полученный в результате запроса из задачи
     * @param task - решаемая задача
     *
     * @return true, если результаты можно считать одинаковыми. Если что-то пошло не так - выбросить ошибку
     */
    protected abstract fun compareEquality(userRs: List<Row>, taskRs: List<Row>, task: Task): Boolean

    /**
     * Получить результаты запроса для проверки
     *
     * @param sql - запрос на получение результатов
     * @param task - решаемая задача, откуда берутся дополнительные данные
     *
     * @return результаты запроса
     */
    protected abstract fun getQueryResults(sql: String, task: Task): List<Row>

    /**
     * Выполнить проверяемый SQL-запрос
     *
     * @param sql - выполняемый запрос
     */
    protected abstract fun doExecuteSql(sql: String)

    /**
     * Получить DML-команду, которую обрабатывает этот конкретный сервис
     *
     * @return DML-команда в enum-представлении
     */
    abstract fun getDmlCommand(): DmlCommandsEnum

    protected open fun selectFromTable(tableName: String): List<Row> =
        jdbcTemplate.query("select * from $tableName", rsExtractor) ?: listOf() // todo мб вставить пустой ряд с названиями колонок?

    companion object {
        const val WRONG_SQL = "Для ожидаемой команды замечена неправильная команда внутри запроса"
        const val FAILED_SQL = "Не удалось выполнить SQL-запрос"
        const val FAILED_TASK_SQL = "Не удалось выполнить эталонный SQL-запрос из задания. " +
                "Обратитесь к своему преподавателю"
        const val SOMETHING_WENT_WRONG = "Что-то пошло не так. Попробуйте позже"
        const val COMPARISON_FAILED = "Введённый запрос не верен"
    }
}