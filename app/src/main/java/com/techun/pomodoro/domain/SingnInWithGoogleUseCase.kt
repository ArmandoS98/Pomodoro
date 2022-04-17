package com.techun.pomodoro.domain

import com.google.firebase.auth.FirebaseUser
import com.techun.pomodoro.data.firebase.BaseAuthRepository
import javax.inject.Inject

class SingnInWithGoogleUseCase @Inject constructor(private val repository: BaseAuthRepository) {
    suspend operator fun invoke(idToken: String): FirebaseUser? {
        return repository.singnInWithGoogle(idToken)
    }
}