package com.techun.pomodoro.di

import com.techun.pomodoro.data.firebase.BaseAuthRepository
import com.techun.pomodoro.data.firebase.BaseAuthenticator
import com.techun.pomodoro.data.firebase.FirebaseAuthenticator
import com.techun.pomodoro.data.firebase.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun provideAuthenticator(): BaseAuthenticator {
        return FirebaseAuthenticator()
    }

    @Singleton
    @Provides
    fun provideRepository(authenticator: BaseAuthenticator): BaseAuthRepository {
        return AuthRepository(authenticator)
    }
}