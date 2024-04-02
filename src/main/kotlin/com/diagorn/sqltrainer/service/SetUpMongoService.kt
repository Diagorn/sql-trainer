package com.diagorn.sqltrainer.service

import com.diagorn.sqltrainer.model.mongo.Role
import com.diagorn.sqltrainer.model.mongo.User
import com.diagorn.sqltrainer.repo.UserRepo
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

/**
 * Сервис подготовки монго к работе со стендом
 *
 * @param defaultAdminPassword - стандартный пароль администратора в незашифрованном виде
 * @param defaultAdminEmail - стандартный емейл админа
 * @param userRepo - репозиторий работы с пользователями
 * @param passwordEncoder - шифратор паролей
 *
 * @author Diagorn
 */
@Service
class SetUpMongoService(
    @Value("\${sqltrainer.security.defaultAdminPassword}")
    private val defaultAdminPassword: String,
    @Value("\${sqltrainer.security.defaultAdminEmail}")
    private val defaultAdminEmail: String,
    private val userRepo: UserRepo,
    private val passwordEncoder: PasswordEncoder
) {

    @PostConstruct
    fun init() {
        val admins = userRepo.findByRole(Role.ADMIN)
        if (admins.isEmpty()) {
            val defaultAdmin = User(
                id = UUID.randomUUID(),
                email = defaultAdminEmail,
                usrPassword = passwordEncoder.encode(defaultAdminPassword),
                role = Role.ADMIN,
                firstName = FIRST_NAME,
                lastName = LAST_NAME,
                middleName = MIDDLE_NAME,
                avatarUrl = null
            )
            userRepo.save(defaultAdmin)
        }
    }

    companion object {
        const val FIRST_NAME = "Админ"
        const val MIDDLE_NAME = "Админович"
        const val LAST_NAME = "Админов"
    }
}