package com.techun.pomodoro.domain

import com.google.firebase.auth.FirebaseUser
import com.techun.pomodoro.data.firebase.BaseAuthRepository
import javax.inject.Inject

class SignUpWithEmailPassUseCase @Inject constructor(private val repository: BaseAuthRepository) {
    suspend operator fun invoke(email: String, password: String): FirebaseUser? {
        return repository.signUpWithEmailPassword(email, password)
    }
}