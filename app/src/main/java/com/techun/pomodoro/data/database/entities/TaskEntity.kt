package com.techun.pomodoro.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.techun.pomodoro.domain.TaskItem

/*
ID
NAME
SHORT DESCRIPTION
PRIORITY
CONFIG POMODO[NO_TASKS, WORK_GAP_MINS,BREAKS]
 */
@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "short_descriptions") val short_descriptions: String,
    @ColumnInfo(name = "priority") val priority: Int,
    @ColumnInfo(name = "no_of_tasks") val no_of_tasks: Int,
    @ColumnInfo(name = "work_gap") val work_gap: Int,
    @ColumnInfo(name = "short_breaks") val short_breaks: Int
)

fun TaskItem.toDatabase() = TaskEntity(
    name = name,
    short_descriptions = short_descriptions,
    priority = priority,
    no_of_tasks = no_of_tasks,
    work_gap = work_gap,
    short_breaks = short_breaks
)
