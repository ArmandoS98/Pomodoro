package com.techun.pomodoro.domain

import com.google.firebase.auth.FirebaseUser
import com.techun.pomodoro.data.firebase.BaseAuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val repository: BaseAuthRepository) {
    suspend operator fun invoke(): FirebaseUser? {
        return repository.getCurrentUser()
    }
}