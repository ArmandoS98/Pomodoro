package com.techun.pomodoro.di

import android.content.Context
import androidx.room.Room
import com.techun.pomodoro.data.database.PomodoroDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val POMODORO_DATABASE_NAME = "pomodoro_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, PomodoroDatabase::class.java, POMODORO_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideQuizDao(db: PomodoroDatabase) = db.getQuizDao()
}