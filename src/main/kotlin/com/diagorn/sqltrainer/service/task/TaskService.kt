package com.diagorn.sqltrainer.service.task

import com.diagorn.sqltrainer.rest.dto.NewTaskRequest
import com.diagorn.sqltrainer.rest.dto.TaskDto
import java.util.UUID

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
     * Получить все задачи
     *
     * @return все задачи вне зависимо от фильтрации и сортировки
     */
    fun getAllTasks(): List<TaskDto>
}