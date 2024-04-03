package com.diagorn.sqltrainer.service.statistics

import com.diagorn.sqltrainer.exception.NotFoundException
import com.diagorn.sqltrainer.model.mongo.Role
import com.diagorn.sqltrainer.repo.TaskRepo
import com.diagorn.sqltrainer.repo.UserAnswerRepo
import com.diagorn.sqltrainer.repo.UserRepo
import com.diagorn.sqltrainer.rest.dto.TaskStatistics
import com.diagorn.sqltrainer.rest.dto.UserStatistics
import org.springframework.stereotype.Service
import java.util.*

@Service
class StatisticsServiceImpl(
    private val taskRepo: TaskRepo,
    private val userRepo: UserRepo,
    private val userAnswerRepo: UserAnswerRepo,
) : StatisticsService {
    override fun getStatisticsForTask(taskId: UUID): TaskStatistics {
        if (!taskRepo.existsById(taskId)) {
            throw NotFoundException("Не удалось найти задачу по id = $taskId")
        }

        val totalUsersCount = userRepo.countByRole(Role.USER)
        val totalTaskAnswers = userAnswerRepo.findAllByTaskId(taskId)
        val correctAnswers = totalTaskAnswers.filter { it.passed }
        val usersTried = totalTaskAnswers.distinctBy { it.userId }.count()
        val usersSucceeded = totalTaskAnswers.distinctBy { it.passed }.distinctBy { it.userId }.count()
        val popularityPosition = getPopularityPosition(taskId)

        return TaskStatistics(
            usersTriedPercent = (usersTried.toDouble() / totalUsersCount.toDouble() * HUNDRED_DOUBLE).toInt(),
            usersSolvedPercent = (usersSucceeded.toDouble() / usersTried.toDouble() * HUNDRED_DOUBLE).toInt(),
            correctAnswersPercent = (correctAnswers.size.toDouble() / totalTaskAnswers.size.toDouble() * HUNDRED_DOUBLE).toInt(),
            popularityPosition = popularityPosition
        )
    }

    override fun getStatisticsForUser(userId: UUID): UserStatistics {
        if (!userRepo.existsByIdAndRole(userId, Role.USER)) {
            throw NotFoundException("Не найдено пользователя с id = $userId")
        }

        val totalTasksCount = taskRepo.count()
        val totalUserAnswers = userAnswerRepo.findAll()
        val tasksTried = totalUserAnswers.distinctBy { it.taskId }.size
        val tasksSolved = totalUserAnswers.filter { it.passed }.distinctBy { it.taskId }.size
        val averageTry = totalUserAnswers
            .groupBy { it.taskId }
            .filter { it.value.any { value ->
                value.passed
            } }
            .map { it.value.size }
            .average()

        return UserStatistics(
            tasksTriedPercent = (tasksTried.toDouble() / totalTasksCount.toDouble() * HUNDRED_DOUBLE).toInt(),
            tasksSolvedPercent = (tasksSolved.toDouble() / tasksTried.toDouble() * HUNDRED_DOUBLE).toInt(),
            tasksTotalSolvedPercent = (tasksSolved.toDouble() / totalTasksCount.toDouble() * HUNDRED_DOUBLE).toInt(),
            averageTry = averageTry.toInt(),
        )
    }

    private fun getPopularityPosition(taskId: UUID): Int {
        val allAnswers = userAnswerRepo.findAll()
        val taskAnswerCountMap = allAnswers.groupBy { it.taskId }.mapValues { it.value.size }
        val sortedAnswerCount = taskAnswerCountMap.toList().sortedByDescending { it.second }
        return sortedAnswerCount.indexOfFirst { it.first == taskId } + 1
    }

    companion object {
        const val HUNDRED_DOUBLE = 100.0
    }
}