package com.techun.pomodoro.data.firebase.repositories

import com.google.firebase.auth.FirebaseUser
import com.techun.pomodoro.data.firebase.BaseAuthRepository
import com.techun.pomodoro.data.firebase.BaseAuthenticator
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authenticator: BaseAuthenticator
) : BaseAuthRepository {
    override suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser? {
        return authenticator.signInWithEmailPassword(email, password)
    }

    override suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser? {
        return authenticator.signUpWithEmailPassword(email, password)
    }

    override suspend fun singnInWithGoogle(idToken: String): FirebaseUser? {
        return authenticator.singnInWithGoogle(idToken)
    }

    override fun signOut(): FirebaseUser? {
        return authenticator.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return authenticator.getUser()
    }

    override suspend fun sendResetPassword(email: String): Boolean {
        authenticator.sendPasswordReset(email)
        return true
    }
}