package com.example.tomgrocery.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tomgrocery.R
import com.example.tomgrocery.databinding.ActivityLoginRegisterBinding
import com.example.tomgrocery.constants.Constants.FRAG_TAG_LOGIN
import com.example.tomgrocery.view.fragment.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLoginRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameContainer, LoginFragment(), FRAG_TAG_LOGIN)
            .commit()

        binding.closeActivity.setOnClickListener {
            finish()
        }
    }

    fun replaceLoginFragment() {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
            .replace(R.id.frameContainer, LoginFragment(), FRAG_TAG_LOGIN)
            .commit()
    }
}