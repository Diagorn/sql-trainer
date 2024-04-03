package com.diagorn.sqltrainer.rest.dto

/**
 * Статистика по задаче
 *
 * @param usersTriedPercent - процент пользователей, которые пытались ее решить, от общего числа пользователей
 * @param usersSolvedPercent - процент пользователей, которые ее решили, от числа пользователей, которые пытались
 * @param correctAnswersPercent - процент корректных решений от общих решений
 * @param popularityPosition - позиция в рейтинге популярности
 *
 * @author Diagorn
 */
data class TaskStatistics(
    val usersTriedPercent: Int,
    val usersSolvedPercent: Int,
    val correctAnswersPercent: Int,
    val popularityPosition: Int,
)

/**
 * Статистика по пользователю
 *
 * @param tasksTriedPercent - процент задач от общего числа, которые пользователь пытался решить
 * @param tasksSolvedPercent - процент решенных задач от задач, которые пользователь пытался решить
 * @param tasksTotalSolvedPercent - процент решенных задач от общего числа задач
 * @param averageTry - среднее количество попыток для решения задачи
 *
 * @author Diagorn
 */
data class UserStatistics(
    val tasksTriedPercent: Int,
    val tasksSolvedPercent: Int,
    val tasksTotalSolvedPercent: Int,
    val averageTry: Int,
)