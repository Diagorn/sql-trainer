package com.diagorn.sqltrainer.service.userAnswerLog

import com.diagorn.sqltrainer.model.mongo.Task
import com.diagorn.sqltrainer.model.mongo.User

/**
 * Сервис работы с логами ответов пользователей
 *
 * @author Diagorn
 */
interface UserAnswerLogService {

    /**
     * Сохранить лог пользовательского ответа
     *
     * @param sql - запрос, который ввёл пользователь
     * @param user - пользователь
     * @param task - решаемая задача
     */
    fun saveUserAnswerLog(sql: String, user: User, task: Task)
}