package com.diagorn.sqltrainer.service.task

import com.diagorn.sqltrainer.exception.WrongFieldsException
import com.diagorn.sqltrainer.model.mongo.Task
import com.diagorn.sqltrainer.model.mongo.TaskTypeEnum
import com.diagorn.sqltrainer.model.sql.DmlCommandsEnum
import com.diagorn.sqltrainer.repo.TaskRepo
import com.diagorn.sqltrainer.rest.dto.NewTaskRequest
import com.diagorn.sqltrainer.rest.dto.TaskDto
import org.springframework.stereotype.Service
import java.util.*

/**
 * Реализация сервиса задач
 *
 * @param taskRepo - репозиторий работы с задачами
 *
 * @author Diagorn
 */
@Service
class TaskServiceImpl(val taskRepo: TaskRepo): TaskService {
    override fun addTask(request: NewTaskRequest): UUID {
        val taskTypes = request.taskTypeIds.map { TaskTypeEnum.ofId(it) }

        if (request.modifyingTable == null) {
            taskTypes.forEach {
                if (TaskTypeEnum.TABLE_NEEDED.contains(it)) {
                    throw WrongFieldsException("Для типа задания ${it.taskTypeName} необходимо указать изменяемую таблицу")
                }
            }
        }

        var orderImportant: Boolean? = null
        if (taskTypes.any { it.sqlOperator == DmlCommandsEnum.SELECT }) {
            orderImportant = taskTypes.any {
                TaskTypeEnum.ORDER_IMPORTANT.contains(it)
            }
        }

        val task = Task(
            id = UUID.randomUUID(),
            taskTypes = taskTypes,
            description = request.description,
            weight = request.weight,
            modifiedTableName = request.modifyingTable,
            sqlQuery = request.solution,
            orderImportant = orderImportant,
        )

        taskRepo.save(task)
        return task.id
    }

    override fun getAllTasks(): List<TaskDto> {
        return taskRepo.findAll().map {
            TaskDto(
                id = it.id,
                taskTypes = it.taskTypes,
                weight = it.weight,
                solution = it.sqlQuery
            )
        }
    }

    companion object {
        private const val ITEMS_ON_PAGE = 15
    }
}