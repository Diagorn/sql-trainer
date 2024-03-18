package com.diagorn.sqltrainer.model.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

/**
 * Задание на SQL-запрос
 *
 * @param id - идентификатор в формате GUID
 * @param taskTypes - типы запроса (категория)
 * @param description - описание задачи
 * @param weight - вес выполненного задания
 * @param sqlQuery - эталонный SQL-запрос
 * @param modifiedTableName - имя таблицы, которую нужно изменить в задании. null для select-запросов
 * @param orderImportant - важен ли порядок (есть ли сортировка). null для не-select-запросов
 *
 * @author Diagorn
 */
@Document(collation = "tasks")
data class Task(
    @Id var id: UUID,
    val taskTypes: List<TaskTypeEnum>,
    val description: String,
    val weight: Double?,
    val sqlQuery: String,
    val modifiedTableName: String?,
    val orderImportant: Boolean?,
)