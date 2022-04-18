package com.techun.pomodoro.domain

import com.techun.pomodoro.data.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke() {
        repository.clearTasks()
    }
}