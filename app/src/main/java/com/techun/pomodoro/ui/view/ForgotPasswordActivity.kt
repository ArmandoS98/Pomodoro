package com.techun.pomodoro.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.techun.pomodoro.R
import com.techun.pomodoro.databinding.ActivityForgotPasswordBinding
import com.techun.pomodoro.ui.extensions.goToActivity
import com.techun.pomodoro.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityForgotPasswordBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listenToChannels()
        binding.btnmSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnmSubmit -> {
                val email = binding.tieUserEmail.text.toString()
                viewModel.verifySendPasswordReset(email)
            }
        }
    }

    private fun listenToChannels() {
        this.lifecycleScope.launch {
            viewModel.allEventsFlow.collect { event ->
                when (event) {
                    is AuthViewModel.AllEvents.Message -> {
                        Toast.makeText(applicationContext, event.message, Toast.LENGTH_SHORT).show()
                        goToActivity<LoginActivity>()
                    }
                    is AuthViewModel.AllEvents.Error -> {
                        binding.apply {
                            println(event.error)
                            /*  resetPassProgressBar.isInvisible = true
                              errorText.text = event.error*/
                        }
                    }
                    is AuthViewModel.AllEvents.ErrorCode -> {
                        if (event.code == 1)
                            binding.apply {
                                println("email should not be empty")
                                /*    userEmailEtvl.error = "email should not be empty!"
                                                        resetPassProgressBar.isInvisible = true*/
                            }
                    }
                }

            }
        }
    }
}