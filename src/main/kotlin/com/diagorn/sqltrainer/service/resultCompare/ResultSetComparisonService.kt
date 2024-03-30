package com.diagorn.sqltrainer.service.resultCompare

import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.sql.ResultSetMetaData

@Service
class ResultSetComparisonService {

    /**
     * Сравнивает результаты двух SQL-запросов на SELECT
     *
     * @param userRs - ResultSet, полученный в результате пользовательского запроса
     * @param taskRs - ResultSet, полученный в результате запроса из задания
     * @param orderImportant - важен ли порядок строк в ответе (есть ли сортировка)
     *
     * @return true если результаты можно считать равными друг другу
     */
    fun compareSelect(userRs: ResultSet, taskRs: ResultSet, orderImportant: Boolean): Boolean {
        val userResults = extractData(userRs)
        val taskResults = extractData(taskRs)

        if (userResults.size != taskResults.size) {
            return false
        }

        return if (orderImportant) {
            compareEqualityWithOrder(userResults, taskResults)
        } else {
            compareEqualityWithoutOrder(userResults, taskResults)
        }
    }

    /**
     * Сравнивает результаты двух SQL-запросов на UPDATE
     *
     * @param userRs - ResultSet, полученный в результате пользовательского запроса
     * @param taskRs - ResultSet, полученный в результате запроса из задания
     * @param modifiedTableName - имя таблицы, которая подверглась изменению
     *
     * @return true если результаты можно считать равными друг другу
     */
    fun compareUpdate(userRs: ResultSet, taskRs: ResultSet, modifiedTableName: String): Boolean {
        val userResults = extractData(userRs)
        val taskResults = extractData(taskRs)

        if (userResults.size != taskResults.size) {
            return false
        }

        return compareEqualityWithoutOrder(userResults, taskResults)
    }

    private fun extractData(rs: ResultSet): List<Row> {
        val resultList = ArrayList<LinkedHashMap<String, Any>>()
        val metaData: ResultSetMetaData = rs.metaData
        val columnCount = metaData.columnCount

        while (rs.next()) {
            val row = LinkedHashMap<String, Any>()
            for (i in 1..columnCount) {
                val columnName = metaData.getColumnName(i)
                val value = rs.getObject(i)
                row[columnName] = value
            }
            resultList.add(row)
        }

        return resultList.map { Row(it) }
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

        val userResultsMap = mutableMapOf<LinkedHashMap<String, Any>, Int>()
        val taskResultsMap = mutableMapOf<LinkedHashMap<String, Any>, Int>()

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

private class Row(
    val map: LinkedHashMap<String, Any>
) : Comparable<Row> {
    override fun compareTo(other: Row): Int {
        val thisEntries = map.entries.sortedBy { it.key }
        val otherEntries = other.map.entries.sortedBy { it.key }

        for (i in thisEntries.indices) {
            if (thisEntries[i].key != otherEntries[i].key || thisEntries[i].value != otherEntries[i].value) {
                return 1
            }
        }

        return 0
    }

    fun isEqualTo(other: Row) = this.compareTo(other) == 0

    fun toList() = map.toList()
}