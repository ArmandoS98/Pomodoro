package com.techun.pomodoro.ui.view

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.techun.pomodoro.R
import com.techun.pomodoro.data.sharedPreferences.PreferencesProvider
import com.techun.pomodoro.data.sharedPreferences.SharedPrefHelper
import com.techun.pomodoro.data.utils.TimerState
import com.techun.pomodoro.databinding.FragmentTimerBinding
import com.techun.pomodoro.ui.extensions.getTime
import com.techun.pomodoro.ui.extensions.getTimeMilisInFuture

class TimerFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!

    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds: Long = 0L
    private var timerState = TimerState.Stopped

    private var secondsRemaining = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabPlayAndPause.setOnClickListener(this)
        binding.fabStop.setOnClickListener(this)
    }
/*
    private fun progressBarStatus(progress: Int) {
        val max = binding.progressBar.max
        val realProgress = max - progress
        println("Times: $max ;Progress: $progress")
        binding.progressBar.progress = realProgress
    }*/

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabPlayAndPause -> {
                when (timerState) {
                    TimerState.Running -> {
                        //Parar
                        timer.cancel()
                        timerState = TimerState.Paused
                        updateButtons()
                    }
                    TimerState.Stopped, TimerState.Paused -> {
                        //Iniciar
                        startTimer()
                        timerState = TimerState.Running
                        updateButtons()
                    }
                }
            }
            R.id.fabStop -> {
                if (timerState == TimerState.Paused || timerState == TimerState.Running) {
                    timer.cancel()
                    onTimerFinished()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        initTimer()

        //TODO: remove backgound timer, hide notification
    }

    override fun onPause() {
        super.onPause()
        when (timerState) {
            TimerState.Running -> {
                timer.cancel()
                //TODO: start backgound timer and show notification
            }
            TimerState.Paused -> {
                //TODO: show notification
            }
            else -> {
                //TODO: nothing todo
            }
        }

        PreferencesProvider.setPreviousTimerLengthSeconds(
            requireContext(),
            SharedPrefHelper.PREVIOUS_TIMER_LENGTH_SECONDS_ID,
            timerLengthSeconds
        )

        PreferencesProvider.setSecondsRemaining(
            requireContext(),
            SharedPrefHelper.SECONDS_REMAINING_ID,
            secondsRemaining
        )

        PreferencesProvider.setTimerState(
            requireContext(),
            SharedPrefHelper.TIMER_STATE_ID,
            timerState
        )
    }

    private fun initTimer() {
        timerState =
            PreferencesProvider.getTimerState(requireContext(), SharedPrefHelper.TIMER_STATE_ID)

        if (timerState == TimerState.Stopped)
            setNewTimerLength()
        else
            setPreviousTimerLength()

        secondsRemaining = if (timerState == TimerState.Running || timerState == TimerState.Paused)
            PreferencesProvider.getSecondsRemaining(
                requireContext(),
                SharedPrefHelper.SECONDS_REMAINING_ID
            )
        else
            timerLengthSeconds

        //TODO: change secondsRemaining according to where the background timer stopped

        //resume where we left off
        if (timerState == TimerState.Running)
            startTimer()

        updateButtons()
        updateCountdownUI()
    }

    private fun onTimerFinished() {
        timerState = TimerState.Stopped

        setNewTimerLength()

        binding.progressBar.progress = 0

        PreferencesProvider.setSecondsRemaining(
            requireContext(),
            SharedPrefHelper.SECONDS_REMAINING_ID, timerLengthSeconds
        )

        secondsRemaining = timerLengthSeconds

        updateButtons()
        updateCountdownUI()
    }

    private fun startTimer() {
        timerState = TimerState.Running

        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onTick(millinsUntilFinished: Long) {
                secondsRemaining = millinsUntilFinished / 1000
                updateCountdownUI()
            }

            override fun onFinish() = onTimerFinished()
        }.start()
    }

    private fun setNewTimerLength() {
        val lengthInMinute = PreferencesProvider.getTimerLength(requireContext())
        timerLengthSeconds = (lengthInMinute * 60L)
        binding.progressBar.max = timerLengthSeconds.toInt()
    }

    private fun setPreviousTimerLength() {
        timerLengthSeconds = PreferencesProvider.getPreviousTimerLengthSeconds(
            requireContext(),
            SharedPrefHelper.PREVIOUS_TIMER_LENGTH_SECONDS_ID
        )
        binding.progressBar.max = timerLengthSeconds.toInt()
    }

    private fun updateCountdownUI() {
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        binding.textView2.text =
            "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0$secondsStr"}"
        binding.progressBar.progress = (timerLengthSeconds - secondsRemaining).toInt()
    }

    private fun updateButtons() {
        when (timerState) {
            TimerState.Running -> {
                //TODO: update UI
                binding.fabPlayAndPause.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.outline_pause_24
                    )
                )

            }
            TimerState.Paused, TimerState.Stopped -> {
                //TODO: update UI
                binding.fabPlayAndPause.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.outline_play_arrow_24
                    )
                )
            }
        }
    }
}
