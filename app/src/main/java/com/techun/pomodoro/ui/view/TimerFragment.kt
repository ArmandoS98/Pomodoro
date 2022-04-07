package com.techun.pomodoro.ui.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.techun.pomodoro.R
import com.techun.pomodoro.data.sharedPreferences.PreferencesProvider
import com.techun.pomodoro.data.sharedPreferences.SharedPrefHelper
import com.techun.pomodoro.data.utils.NotificationUtil
import com.techun.pomodoro.data.utils.TimerState
import com.techun.pomodoro.databinding.FragmentTimerBinding
import com.techun.pomodoro.data.receivers.TimerExpireReceiver
import com.techun.pomodoro.ui.extensions.goToActivity
import com.techun.pomodoro.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class TimerFragment : Fragment(), View.OnClickListener {
    companion object {
        fun setAlarm(context: Context, nowSecond: Long, secondsRemaining: Long): Long {
            val wakeUpTime = (nowSecond + secondsRemaining) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpireReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
            PreferencesProvider.setAlarmSetTime(
                context,
                SharedPrefHelper.ALARM_SET_TIME_TO,
                nowSecond
            )
            return wakeUpTime
        }

        fun removeAlarm(context: Context) {
            val intent = Intent(context, TimerExpireReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            PreferencesProvider.setAlarmSetTime(
                context,
                SharedPrefHelper.ALARM_SET_TIME_TO,
                0
            )
        }

        val nowSecond: Long get() = Calendar.getInstance().timeInMillis / 1000
    }

    private val viewModel: AuthViewModel by viewModels()
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
        getUser()
        registerObserver()
        listenToChannels()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabPlayAndPause.setOnClickListener(this)
        binding.fabStop.setOnClickListener(this)
        binding.imgCloseTask.setOnClickListener(this)
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
            R.id.imgCloseTask -> {
                binding.mcvRunningTask.visibility = GONE
                binding.tvCurrentTimer.text = "00:00"
            }
        }
    }

    override fun onResume() {
        super.onResume()

        initTimer()

        removeAlarm(requireContext())
        NotificationUtil.hideTimerNotification(requireContext())
    }

    override fun onPause() {
        super.onPause()
        when (timerState) {
            TimerState.Running -> {
                timer.cancel()
                val wakeUpTime = setAlarm(requireContext(), nowSecond, secondsRemaining)
                NotificationUtil.showTimerRunning(requireContext(),wakeUpTime)
            }
            TimerState.Paused -> {
                NotificationUtil.showTimerPaused(requireContext())
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

        val alarmSetTime = PreferencesProvider.getAlarmSetTime(
            requireContext(),
            SharedPrefHelper.ALARM_SET_TIME_TO
        )
        if (alarmSetTime > 0)
            secondsRemaining -= nowSecond - alarmSetTime

        if (secondsRemaining <= 0)
            onTimerFinished()
        else if (timerState == TimerState.Running)
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
        binding.tvCurrentTimer.text =
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

    //FirebaseAuth
    private fun getUser() {
        viewModel.getCurrentUser()
    }

    private fun listenToChannels() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allEventsFlow.collect { event ->
                when(event){
                    is AuthViewModel.AllEvents.Message ->{
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun registerObserver() {
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.apply {
                   /* welcomeTxt.text = "welcome ${it.email}"
                    signinButton.text = "sign out"
                    signinButton.setOnClickListener {
                        viewModel.signOut()
                    }*/
                }
            } ?: binding.apply {
                requireActivity().goToActivity<LoginActivity>()
            }
        }
    }

}
