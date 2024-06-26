package com.diagorn.sqltrainer.service.user

import com.diagorn.sqltrainer.exception.NotFoundException
import com.diagorn.sqltrainer.exception.WrongFieldsException
import com.diagorn.sqltrainer.model.mongo.Role
import com.diagorn.sqltrainer.model.mongo.User
import com.diagorn.sqltrainer.repo.UserRepo
import com.diagorn.sqltrainer.rest.dto.EditUserRequest
import com.diagorn.sqltrainer.rest.dto.NewUserRequest
import com.diagorn.sqltrainer.rest.dto.UserDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

/**
 * Реализация сервиса работы с пользователями
 *
 * @param userRepo - репозиторий работы с пользователями
 * @param passwordEncoder - шифратор паролей
 *
 * @author Diagorn
 */
@Service
class UserServiceImpl(
    val userRepo: UserRepo,
    val passwordEncoder: PasswordEncoder
): UserService {

    val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun registerUser(request: NewUserRequest): UUID {
        if (userRepo.existsByEmail(request.email)) {
            throw WrongFieldsException("Пользователь с email ${request.email} уже существует")
        }

        val user = User(
            id = UUID.randomUUID(),
            email = request.email,
            usrPassword = passwordEncoder.encode(request.password),
            role = Role.USER,
            firstName = request.firstName,
            lastName = request.lastName,
            middleName = request.middleName,
            avatarUrl = null,
        )

        userRepo.save(user)

        logger.info("Создан новый пользователь с ID = ${user.id}")

        // todo send email + log

        return user.id
    }

    override fun editUser(request: EditUserRequest): UserDto {
        val user = getById(request.id)

        val editedUser = User(
            id = user.id,
            email = user.email,
            usrPassword = user.usrPassword,
            role = user.role,
            firstName = request.firstName,
            lastName = request.lastName,
            middleName = request.middleName,
            avatarUrl = request.avatarUrl
        )

        userRepo.save(editedUser)
        logger.info("Отредактирован пользователь с ID = ${request.id}")

        return editedUser.toDto()
    }

    override fun findUsers(): List<UserDto> =
        userRepo.findAll().map { it.toDto() }

    override fun findById(userId: UUID): User {
        val userFromDB = userRepo.findById(userId)
        if (userFromDB.isEmpty) {
            throw NotFoundException("Пользователь с id = $userId не найден")
        }
        return userFromDB.get()
    }

    private fun getById(id: UUID): User {
        return userRepo.findByIdOrNull(id) ?: throw NotFoundException("Не найден пользователь с ID = $id")
    }
}