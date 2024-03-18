package com.diagorn.sqltrainer.model.mongo

import com.diagorn.sqltrainer.rest.dto.UserDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

/**
 * Пользователь
 *
 * @param id - идентификатор в формате GUID
 * @param email - e-mail пользователя с сайта mpei.ru
 * @param password - пароль пользователя
 * @param role - роль пользователя
 * @param firstName - имя пользователя
 * @param lastName - фамилия пользователя
 * @param middleName - отчество пользователя
 * @param avatarUrl - ссылка на аватар пользователя
 *
 * @author Diagorn
 */
@Document(collection = "users")
data class User(
    @Id val id: UUID,
    val email: String,
    val password: String,
    val role: Role,
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val avatarUrl: String?,
) {
    fun toDto(): UserDto = UserDto(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        middleName = this.middleName,
        avatarUrl = this.avatarUrl,
        role = this.role
    )
}
