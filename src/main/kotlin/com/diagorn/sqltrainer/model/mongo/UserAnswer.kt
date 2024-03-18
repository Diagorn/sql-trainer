package com.diagorn.sqltrainer.model.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

/**
 * Пользовательский ответ на задачу
 *
 * @param id - ид ответа
 * @param taskId - ид решаемой задачи
 * @param passed - прошёл ли ответ
 * @param executionTimeDifference - процент скорости исполнения запроса от скорости исполнения эталонного решения
 * @param answerLengthDifference - процент длины запроса от длины эталонного решения
 */
@Document(collection = "userAnswers")
data class UserAnswer(
    @Id var id: UUID,
    val taskId: UUID,
    val passed: Boolean?,
    val executionTimeDifference: Double?,
    val answerLengthDifference: Double?,
)