package com.diagorn.sqltrainer.rest.dto

import com.diagorn.sqltrainer.model.mongo.Role
import java.util.UUID

/**
 * Запрос на создание нового пользователя
 *
 * @param email - email с сайта mpei.ru
 * @param password - пароль
 * @param firstName - имя пользователя
 * @param lastName - фамилия пользователя
 * @param middleName - отчество пользователя
 *
 * @author Diagorn
 */
data class NewUserRequest(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val middleName: String?,
)

/**
 * Запрос на логин
 *
 * @param email - email с сайта mpei.ru
 * @param password - пароль
 */
data class LoginRequest(
    val email: String,
    val password: String,
)

/**
 * Ответ на логин
 *
 * @param token - JWT-токен
 * @param userId - ид пользователя
 * @param email - email пользователя
 * @param role - роль пользователя
 */
data class LoginResponse(
    val token: String,
    val userId: String,
    val email: String,
    val role: String
)

/**
 * Запрос на редактирование пользователя
 *
 * @param id - ид пользователя
 * @param firstName - имя пользователя
 * @param lastName - фамилия пользователя
 * @param middleName - отчество пользователя
 * @param avatarUrl - ссылка на аватар пользователя
 *
 * @author Diagorn
 */
data class EditUserRequest(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val avatarUrl: String?,
)

/**
 * Представление пользователя
 *
 * @param id - ид пользователя
 * @param email - email с сайта mpei.ru
 * @param firstName - имя пользователя
 * @param lastName - фамилия пользователя
 * @param middleName - отчество пользователя
 * @param avatarUrl - ссылка на аватар пользователя
 * @param role - роль пользователя
 *
 * @author Diagorn
 */
data class UserDto(
    val id: UUID,
    val email: String,
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val avatarUrl: String?,
    val role: Role,
)