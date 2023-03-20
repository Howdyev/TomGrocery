package com.example.tomgrocery.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.tomgrocery.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({ // This method will be executed once the timer is over
            val i = Intent(applicationContext, WelcomeActivity::class.java)
            startActivity(i)
            finish()
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }, 3000)
    }
}