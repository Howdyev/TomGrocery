package com.example.tomgrocery.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import com.example.tomgrocery.model.remote.dto.LoginData
import com.example.tomgrocery.R
import com.example.tomgrocery.databinding.FragmentLoginBinding
import com.example.tomgrocery.constants.Constants.FRAG_TAG_SIGNUP
import com.example.tomgrocery.util.MyToast
import com.example.tomgrocery.util.MyVibrator
import com.example.tomgrocery.util.localstorage.LocalStorage
import com.example.tomgrocery.view.activity.DashboardActivity
import com.example.tomgrocery.view.activity.LoginRegisterActivity
import com.example.tomgrocery.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment: Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var containerActivity: LoginRegisterActivity
    @Inject lateinit var localStorage: LocalStorage
    private val viewModel: AuthViewModel by viewModels()

    private lateinit var shakeAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        containerActivity = requireActivity() as LoginRegisterActivity
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupObserver()

        localStorage.count++
        Log.i("pmh", "LocalStorage ${localStorage.count}")
    }

    private fun setupObserver() {
        viewModel.apiResultMessage.observe(viewLifecycleOwner) {
            if(!it.isEmpty()) {
                MyToast.showToast(requireContext(), it)
            }
        }
        viewModel.userInfo.observe(viewLifecycleOwner) {
            it?.let {
                localStorage.saveLoginInfo(
                    firstName = it.user.firstName,
                    email = it.user.email,
                    phone = it.user.mobile,
                    userId = it.user._id,
                    token = it.token,
                    firstTime =  true
                )
                startActivity(Intent(activity, DashboardActivity::class.java))
                containerActivity.finish()
            }
        }
        viewModel.isProcessing.observe(viewLifecycleOwner) {
            if(it) {
                binding.progressBar.root.visibility = View.VISIBLE
            } else {
                binding.progressBar.root.visibility = View.GONE
            }
        }
    }

    private fun initViews() {
        shakeAnimation = AnimationUtils.loadAnimation(requireActivity(), R.anim.shake)
        binding.createAccount.setOnClickListener {
            openSignUpScreen()
        }
        binding.loginBtn.setOnClickListener {
            checkValidation()
        }
    }

    private fun login() {
        val loginData = LoginData(binding.loginEmail.text.toString(),binding.loginPassword.text.toString())
        viewModel.login(loginData)
    }

    private fun checkValidation() {
        val email = binding.loginEmail.text.toString()
        val password = binding.loginPassword.text.toString()

        if(email.isEmpty() || password.isEmpty()) {
            binding.loginLayout.startAnimation(shakeAnimation)
            MyToast.showToast(requireContext(), "Enter both credentials.")
            MyVibrator.vibrate(requireActivity(), 300)
        } else {
            login()
        }
    }

    private fun openSignUpScreen() {
        containerActivity.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
            .replace(R.id.frameContainer, SignUpFragment(), FRAG_TAG_SIGNUP)
            .commit()
    }
}