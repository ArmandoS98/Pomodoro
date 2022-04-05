package com.techun.pomodoro.ui.extensions

import android.app.Activity
import android.content.Intent
import java.util.concurrent.TimeUnit

fun Int.getTimeMilisInFuture() = ((this * 60) * 1000).toLong()

fun Long.getTime() = String.format(
    "%02d:%02d",
    (TimeUnit.MILLISECONDS.toMinutes(this) - TimeUnit.HOURS.toMinutes(
        TimeUnit.MILLISECONDS.toHours(
            this
        )
    )),
    (TimeUnit.MILLISECONDS.toSeconds(this) - TimeUnit.MINUTES.toSeconds(
        TimeUnit.MILLISECONDS.toMinutes(
            this
        )
    ))
)

inline fun <reified T : Activity> Activity.goToActivity(noinline init: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
    finish()
}






