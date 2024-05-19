package com.diagorn.sqltrainer.rest.controller

import com.diagorn.sqltrainer.rest.dto.*
import com.diagorn.sqltrainer.service.task.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
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
    @PreAuthorize("hasAuthority('ADMIN')")
    fun addNewTask(@RequestBody request: NewTaskRequest): ResponseEntity<UUID> {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addTask(request))
    }

    @GetMapping
    fun getAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "15") size: Int,
    ): ResponseEntity<List<TaskForStudentDto>> {
        return ResponseEntity.ok(taskService.getAllTasks(
            page = page,
            size = size
        ))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<TaskDto> {
        return ResponseEntity.ok(taskService.getById(UUID.fromString(id)))
    }

    @GetMapping("/{id}/edit")
    fun getByIdForEdit(@PathVariable id: String): ResponseEntity<TaskForEditDto> {
        return ResponseEntity.ok(taskService.getByIdForEdit(UUID.fromString(id)));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun editTask(@PathVariable id: String, @RequestBody editTaskRequest: EditTaskRequest) {
        taskService.editTask(UUID.fromString(id), editTaskRequest)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun deleteTask(@PathVariable id: String) {
        taskService.deleteById(UUID.fromString(id))
    }

    @GetMapping("/pages")
    fun getPagesCount(@RequestParam(defaultValue = "15") limit: Int): ResponseEntity<Int> {
        return ResponseEntity.ok(taskService.getPagesCount(limit))
    }

    @GetMapping("/taskTypes")
    fun getTaskTypes(): ResponseEntity<List<TaskTypeDto>> {
        return ResponseEntity.ok(taskService.getTaskTypes())
    }
}