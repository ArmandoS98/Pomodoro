package com.techun.pomodoro.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.techun.pomodoro.data.database.entities.TaskEntity

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_table")
    suspend fun getAllQuiz(): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quiz: TaskEntity): Long

    @Query("DELETE FROM task_table")
    suspend fun deleteAllQuizzies()
}