package com.example.tomgrocery.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.tomgrocery.R
import com.example.tomgrocery.view.adapter.ViewPagerAdapter
import com.example.tomgrocery.databinding.ActivityWelcomeBinding
import com.example.tomgrocery.util.localstorage.LocalStorage
import java.util.*
import javax.inject.Inject

class WelcomeActivity: AppCompatActivity() {
    private lateinit var localStorage: LocalStorage
    private lateinit var timer: Timer
    private var pagePosition = 0
    private var pageCount:Int = 3

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        localStorage = LocalStorage(applicationContext)
        initViews()
    }

    private fun initViews() {
        timer = Timer()
        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter
        scheduleSlider()
        binding.btnStart.setOnClickListener {
            if(localStorage.hasLoginInfo()) {
                startActivity(Intent(applicationContext, DashboardActivity::class.java))
            } else {
                startActivity(Intent(applicationContext, LoginRegisterActivity::class.java))
            }
            finish()
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }
    }

    private fun scheduleSlider() {
        val handler = Handler()

        val update = Runnable {
            if (pagePosition == pageCount) {
                pagePosition = 0
            } else {
                pagePosition = pagePosition + 1
            }
            binding.viewPager.setCurrentItem(pagePosition, true)
        }

        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 500, 4000)
    }

    override fun onStop() {
        timer.cancel()
        super.onStop()
    }

    override fun onPause() {
        timer.cancel()
        super.onPause()
    }
}