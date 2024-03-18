package com.diagorn.sqltrainer.model.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

/**
 * Лог пользовательского ответа
 *
 * @param id - идентификатор в формате GUID
 * @param createDate - дата и время пользовательского ответа
 * @param sqlQuery - запрос, введённый пользователем
 * @param userId - ид пользователя
 * @param taskId - ид выполняемого задания
 *
 * @author Diagorn
 */
@Document(collection = "userAnswerLogs")
data class UserAnswerLog(
    @Id val id: UUID,
    val createDate: LocalDateTime,
    val sqlQuery: String,
    val userId: UUID,
    val taskId: UUID,
)
