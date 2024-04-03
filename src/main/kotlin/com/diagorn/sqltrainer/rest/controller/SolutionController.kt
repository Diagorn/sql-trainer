package com.diagorn.sqltrainer.rest.controller

import com.diagorn.sqltrainer.exception.BadStateException
import com.diagorn.sqltrainer.model.mongo.User
import com.diagorn.sqltrainer.model.sql.DmlCommandsEnum
import com.diagorn.sqltrainer.rest.dto.SolutionRequest
import com.diagorn.sqltrainer.rest.dto.TaskResult
import com.diagorn.sqltrainer.service.check.AbstractSqlCheckService
import com.diagorn.sqltrainer.service.task.TaskService
import com.diagorn.sqltrainer.service.userAnswer.UserAnswerService
import jakarta.annotation.PostConstruct
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * Контроллер работы с пользовательскими ответами
 *
 * @param userAnswerService - сервис работы с пользовательскими ответами
 * @param checkServices - сервисы обработки пользовательских ответов
 * @param taskService - сервис работ с задачами
 *
 * @author Diagorn
 */
@RestController
@RequestMapping("/api/v1/solution")
class SolutionController(
    private val userAnswerService: UserAnswerService,
    private val checkServices: List<AbstractSqlCheckService>,
    private val taskService: TaskService,
) {
    private lateinit var checkServiceMap: Map<DmlCommandsEnum, AbstractSqlCheckService>
    @PostConstruct
    fun init() {
        checkServiceMap = checkServices.associateBy { it.getDmlCommand() }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    fun registerNewAttempt(
        @RequestBody solutionRequest: SolutionRequest,
        @AuthenticationPrincipal user: User
    ): ResponseEntity<TaskResult> {
        val task = taskService.getEntityById(solutionRequest.taskId)
        val checkService = checkServiceMap[task.getPrimaryTaskType().sqlOperator]
            ?: throw BadStateException("Не удалось получить SQL-оператор для решения задачи")

        val executionResult = checkService.execute(solutionRequest.userSql, task)
        userAnswerService.saveSolution(user, executionResult, task)
        return ResponseEntity.ok(executionResult)
    }

    @GetMapping("/{taskId}")
    fun getLatestSolution(
        @PathVariable taskId: String,
        @AuthenticationPrincipal user: User
    ): ResponseEntity<TaskResult> {
        return ResponseEntity.ok(userAnswerService.getLastSolution(user, UUID.fromString(taskId)))
    }
}