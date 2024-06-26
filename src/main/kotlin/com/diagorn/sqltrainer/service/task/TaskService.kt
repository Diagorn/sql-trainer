package com.diagorn.sqltrainer.service.task

import com.diagorn.sqltrainer.model.mongo.Task
import com.diagorn.sqltrainer.rest.dto.*
import java.util.*

/**
 * Сервис работы с задачами
 *
 * @author Diagorn
 */
interface TaskService {
    /**
     * Добавление новой задачи
     *
     * @param request - запрос на добавление
     *
     * @return ид новой задачи
     */
    fun addTask(request: NewTaskRequest): UUID

    /**
     * Получить все задачи для пользователя
     *
     * @param page - номер страницы
     * @param size - количество задач на странице
     *
     * @return все задачи
     */
    fun getAllTasks(page: Int, size: Int): List<TaskForStudentDto>

    /**
     * Получить задачу по идентификатору
     *
     * @param id - идентификатор задачи
     *
     * @return представление задачи
     */
    fun getById(id: UUID): TaskDto

    /**
     * Получить объект задачи
     *
     * @param id - идентификатор задачи
     *
     * @return задачу
     */
    fun getEntityById(id: UUID): Task

    /**
     * Отредактировать существующую задачу
     *
     * @param id - идентификатор задачи
     * @param request - запрос на изменение полей
     *
     * @return представление задачи
     */
    fun editTask(id: UUID, request: EditTaskRequest): TaskDto

    /**
     * Удалить задачу по ид
     *
     * @param id - ид задачу
     */
    fun deleteById(id: UUID)

    /**
     * Получить количество страниц
     *
     * @param limit - количество задач на страницу
     *
     * @return количество страниц с таким количеством задач
     */
    fun getPagesCount(limit: Int): Int

    /**
     * Получить все категории задач
     */
    fun getTaskTypes(): List<TaskTypeDto>

    /**
     * Получить задачу для редактирования
     */
    fun getByIdForEdit(taskId: UUID): TaskForEditDto
}