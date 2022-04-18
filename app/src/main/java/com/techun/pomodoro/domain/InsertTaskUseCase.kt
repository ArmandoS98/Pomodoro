package com.techun.pomodoro.domain

import com.techun.pomodoro.data.TaskRepository
import com.techun.pomodoro.data.database.entities.toDatabase
import javax.inject.Inject

class InsertTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(task: TaskItem): Long {
        val insert = repository.insertTask(task.toDatabase())
        return if (insert > 0) {
            insert
        } else
            -1
    }
}