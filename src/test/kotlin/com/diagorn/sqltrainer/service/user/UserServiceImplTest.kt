package com.diagorn.sqltrainer.service.user

import com.diagorn.sqltrainer.model.mongo.Role
import com.diagorn.sqltrainer.model.mongo.User
import com.diagorn.sqltrainer.repo.UserRepo
import com.diagorn.sqltrainer.rest.dto.NewUserRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.assertj.core.api.Assertions.*
import org.mockito.BDDMockito.*

import java.util.*

class UserServiceImplTest {

    private lateinit var userService: UserService;

    @Mock
    private lateinit var userRepo: UserRepo;

    private val passwordEncoder = NoOpPasswordEncoder.getInstance();

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userService = UserServiceImpl(userRepo, passwordEncoder)
    }

    @Test
    fun registerUser() {
        // given
        val request = NewUserRequest(
            email = EMAIL,
            password = PASSWORD,
            firstName = FIRST_NAME,
            lastName = LAST_NAME,
            middleName = null
        )

        val user = User(
            id = UUID.randomUUID(),
            email = EMAIL,
            usrPassword = PASSWORD,
            role = Role.USER,
            firstName = FIRST_NAME,
            lastName = LAST_NAME,
            middleName = null,
            avatarUrl = null
        )

        `when`(userRepo.existsByEmail(EMAIL))
            .thenReturn(false)

        `when`(userRepo.save(user))
            .thenReturn(user)

        // when
        val newId = userService.registerUser(request)

        // then
        then(userRepo).should().existsByEmail(request.email)
        then(userRepo).should().save(any(User::class.java))
        then(userRepo).shouldHaveNoMoreInteractions()
        assertThat(newId).isNotNull()
    }

    companion object {
        val EMAIL = "some@some.ru"
        val PASSWORD = "test_password"
        val FIRST_NAME = "Николай"
        val LAST_NAME = "Рогалёв"
    }
}