package com.techun.pomodoro.data.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.techun.pomodoro.data.utils.TimerState

object PreferencesProvider {
    fun getTimerLength(context: Context): Int{
        //placeholder
        return 25
    }

    fun getSecondsRemaining(context: Context, key: SharedPrefHelper): Long {
        return prefs(context).getLong(key.value, 0)
    }

    fun setSecondsRemaining(context: Context, key: SharedPrefHelper, value: Long) {
        val editor = prefs(context).edit()
        editor.putLong(key.value, value).apply()
    }


    fun getPreviousTimerLengthSeconds(context: Context, key: SharedPrefHelper): Long {
        return prefs(context).getLong(key.value, 0)
    }

    fun setPreviousTimerLengthSeconds(context: Context, key: SharedPrefHelper, value: Long) {
        val editor = prefs(context).edit()
        editor.putLong(key.value, value).apply()
    }

    fun getTimerState(context: Context, key: SharedPrefHelper): TimerState {
        val ordinal = prefs(context).getInt(key.value, 0)
        return TimerState.values()[ordinal]
    }

    fun setTimerState(context: Context, key: SharedPrefHelper, value: TimerState){
        val editor = prefs(context).edit()
        editor.putInt(key.value, value.ordinal).apply()
    }

    fun getAlarmSetTime(context: Context, key: SharedPrefHelper): Long {
        return prefs(context).getLong(key.value, 0)
    }
    fun setAlarmSetTime(context: Context, key: SharedPrefHelper, value: Long) {
        val editor = prefs(context).edit()
        editor.putLong(key.value, value).apply()
    }
    fun set(context: Context, key: SharedPrefHelper, value: String) {
        val editor = prefs(context).edit()
        editor.putString(key.value, value).apply()
    }

    fun string(context: Context, key: SharedPrefHelper): String? {
        return prefs(context).getString(key.value, null)
    }

    fun int(context: Context, key: SharedPrefHelper): Int {
        return prefs(context).getInt(key.value, 50)
    }

    fun intWinner(context: Context, key: SharedPrefHelper): Int {
        return prefs(context).getInt(key.value, 0)
    }

    fun set(context: Context, key: SharedPrefHelper, value: Boolean) {
        val editor = prefs(context).edit()
        editor.putBoolean(key.value, value).apply()
    }

    fun set(context: Context, key: SharedPrefHelper, value: Int) {
        val editor = prefs(context).edit()
        editor.putInt(key.value, value).apply()
    }

    fun bool(context: Context, key: SharedPrefHelper): Boolean {
        return prefs(context).getBoolean(key.value, true)
    }

    fun remove(context: Context, key: SharedPrefHelper) {
        val editor = prefs(context).edit()
        editor.remove(key.value).apply()
    }

    // Elimina las SharedPreferences del dominio app
    fun clear(context: Context) {
        val editor = prefs(context).edit()
        editor.clear().apply()
    }

    // Private
    private fun prefs(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}