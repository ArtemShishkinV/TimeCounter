package com.shishkin.timecounter

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
        Log.i("TIMER", "#ON-CREATE")
        setContentView(R.layout.activity_main)
        countTextView = findViewById(R.id.clockTextView)
        handler = Handler(Looper.getMainLooper())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i("TIMER", "#ON-SAVE")
        outState.putInt("seconds", seconds)
        outState.putInt("minutes", minutes)
        outState.putInt("hours", hours)
        outState.putBoolean("isStarted", isStarted)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("TIMER", "#ON-RESTORE")
        seconds = savedInstanceState.getInt("seconds")
        minutes = savedInstanceState.getInt("minutes")
        hours = savedInstanceState.getInt("hours")
        isStarted = savedInstanceState.getBoolean("isStarted")
        countTextView.text = getOutputTime()
        if (isStarted) handler.postDelayed(updatedTask(), 1000L)
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