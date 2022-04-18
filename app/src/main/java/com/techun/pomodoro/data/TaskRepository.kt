package com.techun.pomodoro.data

import com.techun.pomodoro.data.database.dao.TaskDao
import com.techun.pomodoro.data.database.entities.TaskEntity
import com.techun.pomodoro.domain.TaskItem
import com.techun.pomodoro.domain.toDomain
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {
    suspend fun getAllTasksFromDatabase(): List<TaskItem> {
        val response = taskDao.getAllQuiz()
        return response.map { it.toDomain() }
    }

    suspend fun insertTask(quizzes: TaskEntity): Long {
        return taskDao.insertAll(quizzes)
    }

    suspend fun clearTasks() {
        taskDao.deleteAllQuizzies()
    }
}