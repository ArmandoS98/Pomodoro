package com.techun.pomodoro.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.techun.pomodoro.data.database.dao.TaskDao
import com.techun.pomodoro.data.database.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class PomodoroDatabase : RoomDatabase() {
    abstract fun getQuizDao(): TaskDao
//    abstract fun getRankingDao(): RankingDao
}