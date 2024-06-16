package com.diagorn.sqltrainer.rest.controller

import com.diagorn.sqltrainer.repo.TaskRepo
import com.diagorn.sqltrainer.rest.dto.NewTaskRequest
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext


@SpringBootTest
@AutoConfigureMockMvc
class TasksControllerTest {

    @Autowired
    lateinit var userController: UserController

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    lateinit var taskRepo: TaskRepo

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @WithMockUser(value = "admin@yandex.ru", authorities = ["ADMIN"])
    fun itShouldAddNewTask() {
        // given
        val request = NewTaskRequest(
            title = TITLE,
            taskTypeIds = TASK_TYPE_IDS,
            description = DESCRIPTION,
            weight = WEIGHT,
            solution = SOLUTION,
            modifyingTable = null,
            orderImportant = ORDER_IMPORTANT
        )

        // when
        val resultActions = mockMvc.perform(post("/api/v1/task")
            .contentType(MediaType.APPLICATION_JSON)
            .content(javaToJson(request)!!))

        // then
        resultActions.andExpect(status().is2xxSuccessful)
        val createdTaskNullable = taskRepo.findByTitle(TITLE)
        assertThat(createdTaskNullable).isNotNull
        val createdTask = createdTaskNullable!!
        assertThat(createdTask.id).isNotNull()
        assertThat(createdTask.description).isEqualTo(request.description)
        assertThat(createdTask.title).isEqualTo(request.title)
        assertThat(createdTask.orderImportant).isEqualTo(request.orderImportant.toBoolean())
        assertThat(createdTask.weight).isEqualTo(request.weight)
        assertThat(createdTask.modifiedTableName).isEqualTo(request.modifyingTable)

        // delete created task
        taskRepo.deleteById(createdTask.id)
    }

    private fun javaToJson(`object`: Any): String? {
        return try {
            ObjectMapper().writeValueAsString(`object`)
        } catch (e: JsonProcessingException) {
            fail("Failed java to json")
            null
        }
    }

    companion object {
        const val TITLE = "Тестовое название"
        val TASK_TYPE_IDS = listOf(1, 5)
        const val DESCRIPTION = "Тестовое описание"
        const val WEIGHT = 5.0
        const val SOLUTION = "select * from employee"
        const val ORDER_IMPORTANT = "false"
    }
}