package com.techun.pomodoro.domain

import com.techun.pomodoro.data.firebase.BaseAuthRepository
import javax.inject.Inject

class SendPasswordResetEmailUseCase @Inject constructor(private val repository: BaseAuthRepository) {
    suspend operator fun invoke(email: String): Boolean? {
        return repository.sendResetPassword(email)
    }
}