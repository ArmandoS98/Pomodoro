package com.techun.pomodoro.data.model

data class TaskModel(
    val name: String,
    val short_descriptions: String,
    val priority: Int,
    val no_of_tasks: Int,
    val work_gap: Int,
    val short_breaks: Int
)

