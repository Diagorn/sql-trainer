package com.diagorn.sqltrainer.repo

import com.diagorn.sqltrainer.model.mongo.UserAnswerLog
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserAnswerLogRepo: MongoRepository<UserAnswerLog, UUID> {
}