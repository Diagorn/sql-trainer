package com.diagorn.sqltrainer.service.userAnswer

import com.diagorn.sqltrainer.exception.NotFoundException
import com.diagorn.sqltrainer.model.mongo.Task
import com.diagorn.sqltrainer.model.mongo.User
import com.diagorn.sqltrainer.model.mongo.UserAnswer
import com.diagorn.sqltrainer.repo.UserAnswerRepo
import com.diagorn.sqltrainer.rest.dto.TaskResult
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class UserAnswerServiceImpl(
    private val userAnswerRepo: UserAnswerRepo
): UserAnswerService {
    override fun getLastSolution(user: User, taskId: UUID): TaskResult {
        val answers = userAnswerRepo.findAllByTaskId(taskId)
        if (answers.isEmpty()) {
            throw NotFoundException("Для задачи с id = $taskId решений пока нет")
        }

        return answers.maxBy { it.answerDate }.toTaskResult()
    }

    override fun saveSolution(user: User, solution: TaskResult, task: Task) {
        userAnswerRepo.save(UserAnswer(
            id = UUID.randomUUID(),
            taskId = task.id,
            userId = user.id,
            passed = solution.contentsEqual,
            executionTimeDifference = solution.executionTimeDifference,
            answerLengthDifference = solution.contentLengthDifference,
            answerDate = LocalDateTime.now()
        ))
    }
}