package com.example.tomgrocery.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Vibrator
import java.time.Duration

object MyVibrator {
    fun vibrate(activity: Activity, duration: Long) {
        val vib = activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vib.vibrate(duration)
    }
}