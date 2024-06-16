package com.diagorn.sqltrainer.repo

import com.diagorn.sqltrainer.model.mongo.Task
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

/**
 * Репозиторий работы с заданиями
 *
 * @author Diagorn
 */
@Repository
interface TaskRepo: MongoRepository<Task, UUID> {
    /**
     * Найти задачу по её названию
     *
     * @param title - название задачи
     */
    fun findByTitle(title: String): Task?
}