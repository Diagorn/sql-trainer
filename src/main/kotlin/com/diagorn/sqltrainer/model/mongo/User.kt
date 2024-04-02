package com.diagorn.sqltrainer.model.mongo

import com.diagorn.sqltrainer.rest.dto.UserDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

/**
 * Пользователь
 *
 * @param id - идентификатор в формате GUID
 * @param email - e-mail пользователя с сайта mpei.ru
 * @param usrPassword - пароль пользователя
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
    val usrPassword: String,
    val role: Role,
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val avatarUrl: String?,
): UserDetails {
    fun toDto(): UserDto = UserDto(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        middleName = this.middleName,
        avatarUrl = this.avatarUrl,
        role = this.role
    )

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableSetOf(SimpleGrantedAuthority(role.name))

    override fun getPassword(): String = usrPassword

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
