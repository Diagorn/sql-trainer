package com.diagorn.sqltrainer.repo

import com.diagorn.sqltrainer.model.mongo.UserAnswer
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

/**
 * Репозиторий работы с пользовательскими ответами
 *
 * @author Diagorn
 */
@Repository
interface UserAnswerRepo: MongoRepository<UserAnswer, UUID> {
    fun findAllByTaskId(taskId: UUID): List<UserAnswer>

    fun deleteAllByTaskId(taskId: UUID)
}