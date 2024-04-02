package com.diagorn.sqltrainer.repo

import com.diagorn.sqltrainer.model.mongo.Role
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

    /**
     * Найти пользователя по email
     *
     * @param email - заданный email
     *
     * @return пользователя если такой пользователь найден и null если нет
     */
    fun findByEmail(email: String): User?

    /**
     * Найти всех пользователей по роли
     *
     * @param role - роль пользователя
     *
     * @return список всех пользователей с заданной ролью
     */
    fun findByRole(role: Role): List<User>
}