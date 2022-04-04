package com.techun.pomodoro.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.techun.pomodoro.databinding.FragmentTimerBinding
import java.util.concurrent.TimeUnit

class TimerFragment : Fragment() {
    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!
    private var pomodoroCountDownTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (pomodoroCountDownTimer != null) {
            pomodoroCountDownTimer!!.cancel()
        }

        val minutes = 25
        pomodoroCountDownTimer = object : CountDownTimer(minutes.getTimeMilisInFuture(), 1000) {
            override fun onTick(millis: Long) {
//                binding.tvCountDownTimer.text = "${millisUntilFinished / 1000}s"
                /*      val progress = (millisUntilFinished / 1000).toInt()
                      progressBarStatus(progress)
      */

                val progress = (millis / 1000).toInt()
                println("Progress: $progress")
                binding.textView2.text = millis.getTime()
                progressBarStatus(progress)
            }

            override fun onFinish() {
            }
        }
        pomodoroCountDownTimer!!.start()
    }

    private fun progressBarStatus(progress: Int) {
        val max = binding.progressBar.max
        val realProgress = max - progress
        println("Times: $max ;Progress: $progress")
        binding.progressBar.progress = realProgress
    }
}

private fun Int.getTimeMilisInFuture() = ((this * 60) * 1000).toLong()

private fun Long.getTime() = String.format(
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
