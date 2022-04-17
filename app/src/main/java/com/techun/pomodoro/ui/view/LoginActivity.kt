package com.techun.pomodoro.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.techun.pomodoro.R
import com.techun.pomodoro.databinding.ActivityLoginBinding
import com.techun.pomodoro.ui.extensions.goToActivity
import com.techun.pomodoro.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var googleConf: GoogleSignInOptions
    private val GOOGLE_SIGN_IN = 1998

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listenToChannels()
        registerObservers()
        binding.btnmLogin.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
        binding.tvForgotPassword.setOnClickListener(this)
        binding.btnmLoginGoogle.setOnClickListener(this)
    }

    private fun registerObservers() {
        viewModel.currentUser.observe(this) { user ->
            user?.let {
                goToActivity<MainActivity>()
            }
        }
    }

    private fun listenToChannels() {
        this.lifecycleScope.launch {
            viewModel.allEventsFlow.collect { event ->
                when (event) {
                    is AuthViewModel.AllEvents.Error -> {
                        binding.apply {
                            println(event.error)
                            /*     errorTxt.text = event.error
                                                progressBarSignin.isInvisible = true*/
                        }
                    }
                    is AuthViewModel.AllEvents.Message -> {
                        Toast.makeText(applicationContext, event.message, Toast.LENGTH_SHORT).show()
                    }
                    is AuthViewModel.AllEvents.ErrorCode -> {
                        if (event.code == 1)
                            binding.apply {
                                println("email should not be empty")
                                /*    userEmailEtvl.error = "email should not be empty"
                                                        progressBarSignin.isInvisible = true*/
                            }


                        if (event.code == 2)
                            binding.apply {
                                println("password should not be empty")
                                /* userPasswordEtvl.error = "password should not be empty"
                                                        progressBarSignin.isInvisible = true*/
                            }
                    }
                    else -> {
                        println("listenToChannels: No event received so far")
                    }
                }

            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnmLogin -> {
                val email = binding.tieUserEmail.text.toString()
                val password = binding.tieUserPassword.text.toString()
                viewModel.signInUser(email, password)
            }
            R.id.btnmLoginGoogle->{
                googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

                signIn()
            }
            R.id.tvRegister -> {
                goToActivity<RegisterActivity>()
            }
            R.id.tvForgotPassword -> {
                goToActivity<ForgotPasswordActivity>()
            }
        }
    }

    private fun signIn() {
        val googleClient = GoogleSignIn.getClient(this, googleConf)
        googleClient.signOut()
        startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.signInUserGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                println("Google sign in failed $e")
            }
        }
    }
}