package com.diagorn.sqltrainer.rest.controller

import com.diagorn.sqltrainer.rest.dto.TaskStatistics
import com.diagorn.sqltrainer.rest.dto.UserStatistics
import com.diagorn.sqltrainer.service.statistics.StatisticsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * Контроллер работы со статистикой
 *
 * @param statisticsService - сервис предоставления статистики
 *
 * @author Diagorn
 */
@RestController
@RequestMapping("/api/v1/statistics")
class StatisticsController(
    val statisticsService: StatisticsService
) {
    @GetMapping("/user/{userId}")
    fun getUserStatistics(@PathVariable userId: String): ResponseEntity<UserStatistics> {
        return ResponseEntity.ok(statisticsService.getStatisticsForUser(UUID.fromString(userId)))
    }

    @GetMapping("/task/{taskId}")
    fun getTaskStatistics(@PathVariable taskId: String): ResponseEntity<TaskStatistics> {
        return ResponseEntity.ok(statisticsService.getStatisticsForTask(UUID.fromString(taskId)))
    }
}