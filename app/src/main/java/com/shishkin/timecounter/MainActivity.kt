package com.shishkin.timecounter

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var countTextView: TextView
    private lateinit var handler: Handler
    private var isStarted = false
    private var seconds = 0
    private var minutes = 0
    private var hours = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        countTextView = findViewById(R.id.clockTextView)
        handler = Handler(Looper.getMainLooper())
    }


    fun onStartButtonClick(view: View) {
        isStarted = true
        handler.postDelayed(updatedTask(), 1000L)
    }

    fun onStopButtonClick(view: View) {
        isStarted = false
    }

    fun onResetButtonClick(view: View) {
        seconds = 0
        minutes = 0
        hours = 0
        countTextView.text = getOutputTime()
    }

    private fun updatedTask(): Runnable = Runnable {
        if (isStarted) {
            updateTime()
            handler.postDelayed(this.updatedTask(), 1000L)
        }
    }

    private fun updateTime() {
        seconds++
        if (seconds == 60) {
            seconds = 0
            minutes++
        }
        if (minutes == 60) {
            minutes = 0
            hours++
        }
        countTextView.text = getOutputTime()
    }

    private fun getOutputTime(): String {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}