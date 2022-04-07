package com.techun.pomodoro.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.widget.LinearLayout
import android.widget.TextView
import com.techun.pomodoro.R
import com.techun.pomodoro.databinding.ActivityLoginBinding
import com.techun.pomodoro.ui.extensions.goToActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnmLogin.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
        binding.tvForgotPassword.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnmLogin -> {
                goToActivity<MainActivity>()
            }

            R.id.tvRegister -> {
                goToActivity<RegisterActivity>()
            }
            R.id.tvForgotPassword -> {
                goToActivity<ForgotPasswordActivity>()
            }
        }
    }
}