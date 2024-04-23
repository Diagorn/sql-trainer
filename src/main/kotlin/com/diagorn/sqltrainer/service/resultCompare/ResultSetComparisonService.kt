package com.diagorn.sqltrainer.service.resultCompare

import com.diagorn.sqltrainer.model.common.Row
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.sql.ResultSetMetaData

@Service
class ResultSetComparisonService {

    /**
     * Сравнивает результаты двух SQL-запросов на SELECT
     *
     * @param userRs - результат, полученный в результате пользовательского запроса
     * @param taskRs - результат, полученный в результате запроса из задания
     * @param orderImportant - важен ли порядок строк в ответе (есть ли сортировка)
     *
     * @return true если результаты можно считать равными друг другу
     */
    fun compareSelect(userRs: List<Row>, taskRs: List<Row>, orderImportant: Boolean): Boolean {
        if (userRs.size != taskRs.size) {
            return false
        }

        return if (orderImportant) {
            compareEqualityWithOrder(userRs, taskRs)
        } else {
            compareEqualityWithoutOrder(userRs, taskRs)
        }
    }

    /**
     * Сравнивает результаты двух SQL-запросов на UPDATE
     *
     * @param userRs - результат, полученный в результате пользовательского запроса
     * @param taskRs - результат, полученный в результате запроса из задания
     * @param modifiedTableName - имя таблицы, которая подверглась изменению
     *
     * @return true если результаты можно считать равными друг другу
     */
    fun compareUpdate(userRs: List<Row>, taskRs: List<Row>, modifiedTableName: String): Boolean {
        if (userRs.size != taskRs.size) {
            return false
        }

        return compareEqualityWithoutOrder(userRs, taskRs)
    }

    private fun compareEqualityWithOrder(
        userResults: List<Row>,
        taskResults: List<Row>,
    ): Boolean {
        if (emptyResults(userResults, taskResults)) {
            return true
        }

        if (wrongColumnOrder(userResults, taskResults)) {
            return false
        }

        // Проверяем каждый ряд на соответствие другому
        for (i in 0..userResults.size) {
            if (!userResults[i].isEqualTo(taskResults[i])) {
                return false
            }
        }

        return true
    }

    private fun compareEqualityWithoutOrder(
        userResults: List<Row>,
        taskResults: List<Row>,
    ): Boolean {
        if (emptyResults(userResults, taskResults)) {
            return true
        }

        val userResultsMap = mutableMapOf<LinkedHashMap<String, Any?>, Int>()
        val taskResultsMap = mutableMapOf<LinkedHashMap<String, Any?>, Int>()

        for (row in userResults) {
            userResultsMap.merge(row.map, 1) { a, b -> a + b }
        }

        for (row in taskResults) {
            taskResultsMap.merge(row.map, 1) { a, b -> a + b }
        }

        return userResultsMap == taskResultsMap
    }

    private fun emptyResults(
        userResults: List<Row>,
        taskResults: List<Row>,
    ): Boolean {
        // Если оба результата ничего не вернули - возвращаем true
        return userResults.isEmpty() && taskResults.isEmpty()
    }

    private fun wrongColumnOrder(
        userResults: List<Row>,
        taskResults: List<Row>,
    ): Boolean {
        val firstUserRow = userResults[0].toList()
        val firstTaskRow = taskResults[0].toList()

        // Если нарушен порядок колонок - возвращаем true
        for (i in 0..firstUserRow.size) {
            if (firstUserRow[i] != firstTaskRow[i]) {
                return true
            }
        }

        return false
    }
}