package com.example.tomgrocery.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.tomgrocery.R
import com.example.tomgrocery.databinding.ActivityDashboardBinding
import com.example.tomgrocery.databinding.NavHeaderMainBinding
import com.example.tomgrocery.util.localstorage.LocalStorage
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var localStorage: LocalStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        localStorage = LocalStorage(applicationContext)
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appbarMain.toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appbarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.setDrawerListener(toggle)
        toggle.syncState()
        val headerView = binding.navHeaderMain.getHeaderView(0)
        val headerBinding = NavHeaderMainBinding.bind(headerView)
        headerBinding.navHeaderName.text = localStorage.getUserName()
        binding.navFooterMain.footerText.setOnClickListener {
            localStorage.logoutUser()
            startActivity(Intent(this, LoginRegisterActivity::class.java))
            finish()
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
}