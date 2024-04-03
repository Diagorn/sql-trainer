package com.diagorn.sqltrainer.service.statistics

import com.diagorn.sqltrainer.rest.dto.TaskStatistics
import com.diagorn.sqltrainer.rest.dto.UserStatistics
import java.util.UUID

/**
 * Сервис предоставления статистики
 *
 * @author Diagorn
 */
interface StatisticsService {
    /**
     * Получить статистику по задаче
     *
     * @param taskId - ид задачи
     *
     * @return статистику по показателям задачи
     */
    fun getStatisticsForTask(taskId: UUID): TaskStatistics

    /**
     * Получить статистику по пользователю
     *
     * @param userId - ид пользователя
     *
     * @return статистику по пользователю
     */
    fun getStatisticsForUser(userId: UUID): UserStatistics
}