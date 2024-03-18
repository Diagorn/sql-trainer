package com.diagorn.sqltrainer.model.mongo

/**
 * Роль пользвателя
 *
 * @author Diagorn
 */
enum class Role {
    /**
     *  Пользователь (студент)
     */
    USER,

    /**
     * Администратор (преподаватель)
     */
    ADMIN,
}