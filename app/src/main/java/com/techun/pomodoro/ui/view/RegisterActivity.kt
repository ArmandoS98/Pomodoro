package com.techun.pomodoro.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.techun.pomodoro.R
import com.techun.pomodoro.databinding.ActivityRegisterBinding
import com.techun.pomodoro.ui.extensions.goToActivity
import com.techun.pomodoro.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerObservers()
        listenToChannels()
        binding.btnmContinue.setOnClickListener(this)
        binding.tvTermConditions.setOnClickListener(this)
        binding.tvPrivacyPolicy.setOnClickListener(this)
        binding.tvLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnmContinue -> {
                val email = binding.tieUserEmail.text.toString()
                val password = binding.tieUserPassword.text.toString()
                val confirmPass = password
                viewModel.signUpUser(email, password, confirmPass)
            }
            R.id.tvTermConditions -> {
                Toast.makeText(this, "Show terms and conditions", Toast.LENGTH_LONG).show()
            }
            R.id.tvPrivacyPolicy -> {
                Toast.makeText(this, "Show Privacy Policy", Toast.LENGTH_LONG).show()
            }
            R.id.tvLogin -> {
                goToActivity<LoginActivity>()
            }
        }
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
                            /* errorTxt.text = event.error
                                                progressBarSignup.isInvisible = true*/
                        }
                    }
                    is AuthViewModel.AllEvents.Message -> {
                        Toast.makeText(applicationContext, event.message, Toast.LENGTH_SHORT).show()
                    }
                    is AuthViewModel.AllEvents.ErrorCode -> {
                        if (event.code == 1)
                            binding.apply {
                                println("email should not be empty")
                                /*userEmailEtvl.error = "email should not be empty"
                                progressBarSignup.isInvisible = true*/
                            }


                        if (event.code == 2)
                            binding.apply {
                                println("password should not be empty")
                                /* userPasswordEtvl.error = "password should not be empty"
                                 progressBarSignup.isInvisible = true*/
                            }

                        if (event.code == 3)
                            binding.apply {
                                println("passwords do not match")
                                /* confirmPasswordEtvl.error = "passwords do not match"
                                 progressBarSignup.isInvisible = true*/
                            }
                    }

                    else -> {
                        println("listenToChannels: No event received so far")
                    }
                }

            }
        }
    }
}