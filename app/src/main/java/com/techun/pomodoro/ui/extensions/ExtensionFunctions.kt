package com.techun.pomodoro.ui.extensions

import android.app.Activity
import android.content.Intent
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.techun.pomodoro.R
import java.util.concurrent.TimeUnit

fun ImageView.loadByResource(resource: Int) =
    Glide.with(this)
        .load(resource)
        .placeholder(R.drawable.outline_settings_24)
        .error(R.drawable.outline_settings_24)
        .fallback(R.drawable.outline_settings_24)
        .into(this)


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






