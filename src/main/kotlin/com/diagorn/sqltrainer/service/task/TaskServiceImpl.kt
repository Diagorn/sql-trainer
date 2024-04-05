package com.diagorn.sqltrainer.service.task

import com.diagorn.sqltrainer.exception.NotFoundException
import com.diagorn.sqltrainer.exception.WrongFieldsException
import com.diagorn.sqltrainer.model.mongo.Task
import com.diagorn.sqltrainer.model.mongo.TaskTypeEnum
import com.diagorn.sqltrainer.model.sql.DmlCommandsEnum
import com.diagorn.sqltrainer.repo.TaskRepo
import com.diagorn.sqltrainer.repo.UserAnswerRepo
import com.diagorn.sqltrainer.repo.UserRepo
import com.diagorn.sqltrainer.rest.dto.EditTaskRequest
import com.diagorn.sqltrainer.rest.dto.NewTaskRequest
import com.diagorn.sqltrainer.rest.dto.TaskDto
import com.diagorn.sqltrainer.rest.dto.TaskForStudentDto
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*
import kotlin.math.ceil

/**
 * Реализация сервиса задач
 *
 * @param taskRepo - репозиторий работы с задачами
 * @param userRepo - репозиторий работы с пользователями
 * @param userAnswerRepo - репозиторий работы с пользовательскими ответами
 *
 * @author Diagorn
 */
@Service
class TaskServiceImpl(
    val taskRepo: TaskRepo,
    val userRepo: UserRepo,
    val userAnswerRepo: UserAnswerRepo,
): TaskService {
    override fun addTask(request: NewTaskRequest): UUID {
        val taskTypes = request.taskTypeIds.map { TaskTypeEnum.ofId(it) }
        validateTaskTypes(taskTypes)

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
            title = request.title,
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

    override fun editTask(id: UUID, request: EditTaskRequest): TaskDto {
        if (!taskExists(id)) {
            throw getNotFoundException(id)
        }

        val taskTypes = request.taskTypeIds.map { TaskTypeEnum.ofId(it) }
        validateTaskTypes(taskTypes)

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

        val editedTask = Task(
            id = id,
            title = request.title,
            taskTypes = request.taskTypeIds.map { TaskTypeEnum.ofId(it) },
            description = request.description,
            weight = request.weight,
            sqlQuery = request.solution,
            modifiedTableName = request.modifyingTable,
            orderImportant = orderImportant,
        )
        taskRepo.save(editedTask)
        return editedTask.toDto()
    }

    override fun deleteById(id: UUID) {
        if (!taskExists(id)) {
            throw getNotFoundException(id)
        }

        taskRepo.deleteById(id)
    }

    override fun getAllTasks(page: Int, size: Int): List<TaskForStudentDto> {

        // Чтобы не было проблем с поиско и пагинацией
        if (taskRepo.count() == 0L) return listOf()

        val pageRequest = PageRequest.of(page, size)
        val tasksPage = taskRepo.findAll(pageRequest)

        // todo add task results for @AuthenticationPrincipal

        return tasksPage.content.map {
            TaskForStudentDto(
                id = it.id,
                title = it.title,
                taskTypes = it.taskTypes,
                weight = it.weight,
                solved = false, // todo: fix
            )
        }
    }

    override fun getPagesCount(limit: Int): Int {
        val totalTasks = taskRepo.count()
        return ceil(totalTasks.toDouble() / limit).toInt()
    }

    override fun getById(id: UUID): TaskDto = doGetById(id).toDto()
    override fun getEntityById(id: UUID): Task = doGetById(id)

    private fun doGetById(id: UUID): Task = taskRepo.findById(id).orElseThrow { getNotFoundException(id) }

    private fun taskExists(id: UUID): Boolean = taskRepo.existsById(id)

    private fun validateTaskTypes(taskTypes: List<TaskTypeEnum>) {
        val commands = taskTypes.distinctBy { it.sqlOperator }
        if (commands.size > 1) {
            throw WrongFieldsException("Невозможно сохранить задачу с противоречивыми SQL-операторами в решении")
        }
    }

    companion object {
        private fun getNotFoundException(id: UUID) = NotFoundException("Не удалось найти задачу с id = $id")
    }
}