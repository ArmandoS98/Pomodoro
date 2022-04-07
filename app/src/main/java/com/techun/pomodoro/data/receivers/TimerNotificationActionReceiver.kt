package com.techun.pomodoro.data.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.techun.pomodoro.data.sharedPreferences.PreferencesProvider
import com.techun.pomodoro.data.sharedPreferences.SharedPrefHelper
import com.techun.pomodoro.data.utils.AppConstants
import com.techun.pomodoro.data.utils.NotificationUtil
import com.techun.pomodoro.data.utils.TimerState
import com.techun.pomodoro.ui.view.TimerFragment

class TimerNotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            AppConstants.ACTION_STOP -> {
                TimerFragment.removeAlarm(context)
                PreferencesProvider.setTimerState(
                    context,
                    SharedPrefHelper.TIMER_STATE_ID,
                    TimerState.Stopped
                )
                NotificationUtil.hideTimerNotification(context)
            }
            AppConstants.ACTION_PAUSE -> {
                var secondsRemaining = PreferencesProvider.getSecondsRemaining(
                    context,
                    SharedPrefHelper.SECONDS_REMAINING_ID
                )
                val alarmSetTime =
                    PreferencesProvider.getAlarmSetTime(context, SharedPrefHelper.ALARM_SET_TIME_TO)
                val nowSeconds = TimerFragment.nowSecond

                secondsRemaining -= nowSeconds - alarmSetTime
                PreferencesProvider.setSecondsRemaining(
                    context,
                    SharedPrefHelper.SECONDS_REMAINING_ID,
                    secondsRemaining
                )

                TimerFragment.removeAlarm(context)
                PreferencesProvider.setTimerState(
                    context,
                    SharedPrefHelper.TIMER_STATE_ID,
                    TimerState.Paused
                )
                NotificationUtil.showTimerPaused(context)
            }
            AppConstants.ACTION_RESUME -> {
                val secondsRemaining = PreferencesProvider.getSecondsRemaining(
                    context,
                    SharedPrefHelper.SECONDS_REMAINING_ID
                )

                val wakeUpTime = TimerFragment.setAlarm(context, TimerFragment.nowSecond, secondsRemaining)
                PreferencesProvider.setTimerState(context, SharedPrefHelper.TIMER_STATE_ID, TimerState.Running)
                NotificationUtil.showTimerRunning(context,wakeUpTime)
            }
            AppConstants.ACTION_START ->{
                val minutesRemaining = PreferencesProvider.getTimerLength(context)
                val secondsRemaining = minutesRemaining * 60L
                val wakeUpTime = TimerFragment.setAlarm(context, TimerFragment.nowSecond, secondsRemaining)
                PreferencesProvider.setTimerState(context, SharedPrefHelper.TIMER_STATE_ID, TimerState.Running)
                PreferencesProvider.setSecondsRemaining(context,SharedPrefHelper.SECONDS_REMAINING_ID,secondsRemaining)
                NotificationUtil.showTimerRunning(context,wakeUpTime)
            }
        }
    }
}