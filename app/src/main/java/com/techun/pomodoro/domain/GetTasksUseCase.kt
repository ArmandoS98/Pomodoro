package com.techun.pomodoro.domain

import com.techun.pomodoro.data.TaskRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(): List<TaskItem>? {
        val tasks = repository.getAllTasksFromDatabase()

        if (!tasks.isNullOrEmpty()) {
            return tasks
        }
        return null
        /* return if (quizzes.isNotEmpty()) {
             repository.clearQuizzes()
             repository.insertQuizzes(quizzes.map { it.toDatabase() })
             quizzes
         } else {
             repository.getAllQuizzFromDatabase()
         }*/
    }
}