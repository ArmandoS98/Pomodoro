package com.techun.pomodoro.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techun.pomodoro.R
import com.techun.pomodoro.databinding.ActivityLoginBinding
import com.techun.pomodoro.databinding.ActivityRegisterBinding
import com.techun.pomodoro.ui.extensions.goToActivity

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnmContinue.setOnClickListener(this)
        binding.tvTermConditions.setOnClickListener(this)
        binding.tvPrivacyPolicy.setOnClickListener(this)
        binding.tvLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnmContinue -> {
                goToActivity<MainActivity>()
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
}