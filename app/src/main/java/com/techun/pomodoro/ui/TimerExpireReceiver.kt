package com.techun.pomodoro.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.techun.pomodoro.data.sharedPreferences.PreferencesProvider
import com.techun.pomodoro.data.sharedPreferences.SharedPrefHelper
import com.techun.pomodoro.data.utils.NotificationUtil
import com.techun.pomodoro.data.utils.TimerState

class TimerExpireReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationUtil.showTimerExpired(context)

        PreferencesProvider.setTimerState(
            context,
            SharedPrefHelper.TIMER_STATE_ID,
            TimerState.Stopped
        )
        PreferencesProvider.setAlarmSetTime(context, SharedPrefHelper.ALARM_SET_TIME_TO, 0)
    }
}