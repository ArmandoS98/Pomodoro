package com.techun.pomodoro.domain

import com.techun.pomodoro.data.database.entities.TaskEntity
import com.techun.pomodoro.data.model.TaskModel

data class TaskItem(
    val name: String,
    val short_descriptions: String,
    val priority: Int,
    val no_of_tasks: Int,
    val work_gap: Int,
    val short_breaks: Int
)

fun TaskModel.toDomain() = TaskItem(name, short_descriptions, priority, no_of_tasks, work_gap, short_breaks)
fun TaskEntity.toDomain() = TaskItem(name, short_descriptions, priority, no_of_tasks, work_gap, short_breaks)

