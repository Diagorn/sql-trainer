package com.diagorn.sqltrainer.model.mongo

import com.diagorn.sqltrainer.rest.dto.TaskResult
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

/**
 * Пользовательский ответ на задачу
 *
 * @param id - ид ответа
 * @param taskId - ид решаемой задачи
 * @param userId - ид пользователя, чье это решение
 * @param passed - прошёл ли ответ
 * @param answerLengthDifference - процент длины запроса от длины эталонного решения
 * @param answerDate - дата ответа
 */
@Document(collection = "userAnswers")
data class UserAnswer(
    @Id var id: UUID,
    val taskId: UUID,
    val userId: UUID,
    val passed: Boolean,
    val userExecutionTime: Long?,
    val taskExecutionTime: Long?,
    val answerLengthDifference: Double,
    val answerDate: LocalDateTime,
) {
    fun toTaskResult() = TaskResult(
        contentsEqual = this.passed,
        userExecutionTime = this.userExecutionTime,
        taskExecutionTime = this.taskExecutionTime,
        contentLengthDifference = this.answerLengthDifference,
    )
}