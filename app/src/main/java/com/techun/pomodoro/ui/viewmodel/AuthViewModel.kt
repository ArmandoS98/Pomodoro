package com.techun.pomodoro.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.techun.pomodoro.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInWithEmailPassUseCase: SignInWithEmailPassUseCase,
    private val signUpWithEmailPassUseCase: SignUpWithEmailPassUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val sendPasswordResetEmailUseCase: SendPasswordResetEmailUseCase,
    private val singnInWithGoogleUseCase: SingnInWithGoogleUseCase
) : ViewModel() {
    private val TAG = "AuthViewModel"

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val currentUser get() = _firebaseUser
    private val eventsChannel = Channel<AllEvents>()
    val allEventsFlow = eventsChannel.receiveAsFlow()

    fun signInUser(email: String, password: String) = viewModelScope.launch {
        when {
            email.isEmpty() -> {
                eventsChannel.send(AllEvents.ErrorCode(1))
            }
            password.isEmpty() -> {
                eventsChannel.send(AllEvents.ErrorCode(2))
            }
            else -> {
                actualSignInUser(email, password)
            }
        }
    }

    fun signUpUser(email: String, password: String, confirmPass: String) = viewModelScope.launch {
        when {
            email.isEmpty() -> {
                eventsChannel.send(AllEvents.ErrorCode(1))
            }
            password.isEmpty() -> {
                eventsChannel.send(AllEvents.ErrorCode(2))
            }
            password != confirmPass -> {
                eventsChannel.send(AllEvents.ErrorCode(3))
            }
            else -> {
                actualSignUpUser(email, password)
            }
        }
    }

    private fun actualSignInUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val user = signInWithEmailPassUseCase(email, password)
                user?.let {
                    _firebaseUser.postValue(it)
                    eventsChannel.send(AllEvents.Message("login success"))
                }
            } catch (e: Exception) {
                val error = e.toString().split(":").toTypedArray()
                Log.d(TAG, "signInUser: ${error[1]}")
                eventsChannel.send(AllEvents.Error(error[1]))
            }
        }
    }

    private fun actualSignUpUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val user = signUpWithEmailPassUseCase(email, password)
                user?.let {
                    _firebaseUser.postValue(it)
                    eventsChannel.send(AllEvents.Message("sign up success"))
                }
            } catch (e: Exception) {
                val error = e.toString().split(":").toTypedArray()
                Log.d(TAG, "signInUser: ${error[1]}")
                eventsChannel.send(AllEvents.Error(error[1]))
            }
        }
    }

    fun signOut() = viewModelScope.launch {
        try {
            val user = signOutUseCase()
            user?.let {
                eventsChannel.send(AllEvents.Message("logout failure"))
            } ?: eventsChannel.send(AllEvents.Message("sign out successful"))
            getCurrentUser()
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(TAG, "signInUser: ${error[1]}")
            eventsChannel.send(AllEvents.Error(error[1]))
        }
    }

    fun getCurrentUser() = viewModelScope.launch {
        val user = getCurrentUserUseCase()
        _firebaseUser.postValue(user)
    }

    fun verifySendPasswordReset(email: String) {
        if (email.isEmpty()) {
            viewModelScope.launch {
                eventsChannel.send(AllEvents.ErrorCode(1))
            }
        } else {
            sendPasswordResetEmail(email)
        }

    }

    private fun sendPasswordResetEmail(email: String) = viewModelScope.launch {
        try {
            val result = sendPasswordResetEmailUseCase(email)
            if (result!!) {
                eventsChannel.send(AllEvents.Message("reset email sent"))
            } else {
                eventsChannel.send(AllEvents.Error("could not send password reset"))
            }
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(TAG, "signInUser: ${error[1]}")
            eventsChannel.send(AllEvents.Error(error[1]))
        }
    }

    fun signInUserGoogle(idToken: String) {
        viewModelScope.launch {
            try {
                val user = singnInWithGoogleUseCase(idToken)
                user?.let {
                    _firebaseUser.postValue(it)
                    eventsChannel.send(AllEvents.Message("login success"))
                }
            } catch (e: Exception) {
                val error = e.toString().split(":").toTypedArray()
                Log.d(TAG, "signInUser: ${error[1]}")
                eventsChannel.send(AllEvents.Error(error[1]))
            }
        }
    }

    sealed class AllEvents {
        data class Message(val message: String) : AllEvents()
        data class ErrorCode(val code: Int) : AllEvents()
        data class Error(val error: String) : AllEvents()
    }

}