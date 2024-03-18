package com.diagorn.sqltrainer.rest.dto

import com.diagorn.sqltrainer.model.mongo.TaskTypeEnum
import java.util.UUID

/**
 * Результат проверки
 *
 * @param contentsEqual - равен ли между собой контент попытки студента и эталонного решения
 * @param executionTimeDifference - процент скорости исполнения запроса от скорости исполнения эталонного решения
 * @param contentLengthDifference - процент длины запроса от длины эталонного решения
 *
 * @author Diagorn
 */
data class TaskResult(
    val contentsEqual: Boolean,
    val executionTimeDifference: Double,
    val contentLengthDifference: Double,
)

/**
 * Запрос на создание новой задачи
 *
 * @param taskTypeIds - идентификаторы категорий задачи
 * @param description - описание задачи
 * @param weight - вес задачи
 * @param solution - эталонное решение задачи
 * @param modifyingTable - изменяемая запросом таблица
 *
 * @author Diagorn
 */
data class NewTaskRequest(
    val taskTypeIds: List<Int>,
    val description: String,
    val weight: Double?,
    val solution: String,
    val modifyingTable: String?,
)

/**
 * Представление задачи для клиента
 *
 * @param id - ид задачи
 * @param taskTypes - категории задачи
 * @param weight - вес задачи
 * @param solution - эталонное решение задачи
 *
 * @author Diagorn
 */
data class TaskDto(
    val id: UUID,
    val taskTypes: List<TaskTypeEnum>,
    val weight: Double?,
    val solution: String,
)

/**
 * Параметры сортировки задач
 *
 * @param id - ид типа сортировки
 * @param sortTypeName - наименование типа сортировки
 *
 * @author Diagorn
 */
enum class TaskSortType(
    val id: Int,
    val sortTypeName: String
) {
    READY_FIRST(1, "Сначала готовые"),
    UNREADY_FIRST(2, "Сначала неготовые"),
    LOW_MARKS_FIRST(3, "Сначала с низкими оценками"),
    UNREADY_ONLY(4, "Только несданные"),
}
