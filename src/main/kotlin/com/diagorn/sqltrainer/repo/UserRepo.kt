package com.diagorn.sqltrainer.repo

import com.diagorn.sqltrainer.model.mongo.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID

/**
 * Репозиторий работы с пользователями
 *
 * @author Diagorn
 */
interface UserRepo: MongoRepository<User, UUID> {
    /**
     * Проверить, существует ли пользователь с заданным email
     *
     * @param email - заданный email
     *
     * @return true если такой пользователь найден
     */
    fun existsByEmail(email: String): Boolean
}