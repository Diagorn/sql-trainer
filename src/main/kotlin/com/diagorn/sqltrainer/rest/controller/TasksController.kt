package com.diagorn.sqltrainer.rest.controller

import com.diagorn.sqltrainer.rest.dto.NewTaskRequest
import com.diagorn.sqltrainer.rest.dto.TaskDto
import com.diagorn.sqltrainer.service.task.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * Контроллер работы с заданиями
 *
 * @param taskService - сервис заданий
 *
 * @author Diagorn
 */
@RestController
@RequestMapping("/api/v1/task")
class TasksController(val taskService: TaskService) {

    @PostMapping
    fun addNewTask(@RequestBody request: NewTaskRequest): ResponseEntity<UUID> {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addTask(request))
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<TaskDto>> = ResponseEntity.ok(taskService.getAllTasks())
}