package com.techun.pomodoro.ui.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.techun.pomodoro.R
import com.techun.pomodoro.databinding.ActivityForgotPasswordBinding
import com.techun.pomodoro.databinding.ActivityRegisterBinding
import com.techun.pomodoro.ui.extensions.goToActivity

class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnmSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnmSubmit -> {
                goToActivity<LoginActivity>()
            }
        }
    }
}