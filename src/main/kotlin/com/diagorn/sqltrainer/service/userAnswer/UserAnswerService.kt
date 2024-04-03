package com.diagorn.sqltrainer.service.userAnswer

import com.diagorn.sqltrainer.model.mongo.Task
import com.diagorn.sqltrainer.model.mongo.User
import com.diagorn.sqltrainer.rest.dto.TaskResult
import java.util.*

/**
 * Сервис работы с пользовательскими ответами
 *
 * @author Diagorn
 */
interface UserAnswerService {
    /**
     * Получить результаты последнего решения
     *
     * @param user - пользователь, для которого запрашиваются данные
     * @param taskId - ид решенной задачи
     *
     * @return результаты последнего решения
     */
    fun getLastSolution(user: User, taskId: UUID): TaskResult

    /**
     * Сохранить попытку решения задачи
     *
     * @param user - пользователь, который решает задачу
     * @param solution - решение
     */
    fun saveSolution(user: User, solution: TaskResult, task: Task)
}