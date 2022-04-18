package com.techun.pomodoro.domain

import com.techun.pomodoro.data.database.entities.TaskEntity
import com.techun.pomodoro.data.model.TaskModel

data class TaskItem(
    val name: String,
    val short_descriptions: String,
    val priority: Int,
    val no_of_tasks: Int,
    val work_gap: Int,
    val short_breaks: Int,
    val laps_completed: Int,
    val task_completed: Int,
    val iscurrenttask: Int
)

fun TaskModel.toDomain() = TaskItem(
    name,
    short_descriptions,
    priority,
    no_of_tasks,
    work_gap,
    short_breaks,
    laps_completed,
    task_completed,
    iscurrenttask
)

fun TaskEntity.toDomain() = TaskItem(
    name,
    short_descriptions,
    priority,
    no_of_tasks,
    work_gap,
    short_breaks,
    laps_completed,
    task_completed,
    iscurrenttask
)

fun TaskItem.toPendingTask() = if (task_completed == 0)
    TaskItem(
        name,
        short_descriptions,
        priority,
        no_of_tasks,
        work_gap,
        short_breaks,
        laps_completed,
        task_completed,
        iscurrenttask
    ) else null

fun TaskItem.toCompletedTask() = if (task_completed == 1)
    TaskItem(
        name,
        short_descriptions,
        priority,
        no_of_tasks,
        work_gap,
        short_breaks,
        laps_completed,
        task_completed,
        iscurrenttask
    ) else null
