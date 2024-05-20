package com.diagorn.sqltrainer.service.userAnswerLog

import com.diagorn.sqltrainer.model.mongo.Task
import com.diagorn.sqltrainer.model.mongo.User
import com.diagorn.sqltrainer.model.mongo.UserAnswerLog
import com.diagorn.sqltrainer.repo.UserAnswerLogRepo
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class UserAnswerLogServiceImpl(
    private val userAnswerLogRepo: UserAnswerLogRepo
): UserAnswerLogService {
    override fun saveUserAnswerLog(sql: String, user: User, task: Task) {
        userAnswerLogRepo.save(UserAnswerLog(
            UUID.randomUUID(),
            LocalDateTime.now(),
            sql,
            user.id,
            task.id
        ))
    }
}