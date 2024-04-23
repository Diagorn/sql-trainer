package com.diagorn.sqltrainer.model.common

import java.sql.ResultSet
import java.sql.ResultSetMetaData

class Row(
    val map: LinkedHashMap<String, Any?>
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

    companion object {

        fun compareEmpty(first: List<Row>, second: List<Row>): Boolean {
            if (first.isEmpty() && second.isEmpty()) {
                return true
            }

            return first.isNotEmpty() && second.isNotEmpty()
        }

        fun ofResultSet(rs: ResultSet): List<Row> {
            val resultList = ArrayList<LinkedHashMap<String, Any?>>()
            val metaData: ResultSetMetaData = rs.metaData
            val columnCount = metaData.columnCount

            while (rs.next()) {
                val row = LinkedHashMap<String, Any?>()
                for (i in 1..columnCount) {
                    val columnName = metaData.getColumnName(i)
                    val value = rs.getObject(i)
                    row[columnName] = value
                }
                resultList.add(row)
            }

            return resultList.map { Row(it) }
        }
    }
}