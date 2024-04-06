package com.diagorn.sqltrainer.rest.dto

import com.diagorn.sqltrainer.model.mongo.TaskTypeEnum
import com.diagorn.sqltrainer.model.sql.DmlCommandsEnum
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
 * @param title - название задачи
 * @param taskTypeIds - идентификаторы категорий задачи
 * @param description - описание задачи
 * @param weight - вес задачи
 * @param solution - эталонное решение задачи
 * @param modifyingTable - изменяемая запросом таблица
 *
 * @author Diagorn
 */
data class NewTaskRequest(
    val title: String,
    val taskTypeIds: List<Int>,
    val description: String,
    val weight: Double?,
    val solution: String,
    val modifyingTable: String?,
    val orderImportant: String?,
)

/**
 * Запрос на изменение задачи
 *
 * @param title - название
 * @param taskTypeIds - идентификаторы типов задания
 * @param description - описание задания
 * @param weight - вес задания
 * @param solution - эталонное решение
 * @param modifyingTable - изменяемая таблица (в случае запроса не на SELECT)
 * @param orderImportant - важен ли порядок (в случае запроса на SELECT)
 */
data class EditTaskRequest(
    val title: String,
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
 * @param title - название задачи
 * @param taskTypes - категории задачи
 * @param description - описание задачи
 * @param weight - вес задачи
 *
 * @author Diagorn
 */
data class TaskDto(
    val id: UUID,
    val title: String,
    val taskTypes: List<TaskTypeDto>,
    val description: String,
    val weight: Double?,
)

/**
 * Представление задачи для клиента
 *
 * @param id - ид задачи
 * @param title - название задачи
 * @param description - описание задачи
 * @param taskTypes - категории задачи
 * @param weight - вес задачи
 * @param solved - решил ли студент задачу или нет
 *
 * @author Diagorn
 */
data class TaskForStudentDto(
    val id: UUID,
    val title: String,
    val description: String,
    val taskTypes: List<TaskTypeDto>,
    val weight: Double?,
    val solved: Boolean,
)

/**
 * Запрос на решение задачи
 *
 * @param taskId - ид решаемой задачи
 * @param userSql - решение, представленное пользователем
 *
 * @author Diagorn
 */
data class SolutionRequest(
    val taskId: UUID,
    val userSql: String,
)

/**
 * DTO enum TaskTypeEnum
 *
 * @see TaskTypeEnum
 */
data class TaskTypeDto(
    val id: Int,
    val taskTypeName: String,
    val sqlOperatorName: String,
) {
    companion object {
        fun ofEnum(value: TaskTypeEnum): TaskTypeDto {
            return TaskTypeDto(
                id = value.id,
                taskTypeName = value.taskTypeName,
                sqlOperatorName = value.sqlOperator.lowerCaseCommandName
            )
        }
    }
}

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
