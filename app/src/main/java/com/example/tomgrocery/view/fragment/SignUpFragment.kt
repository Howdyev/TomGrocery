package com.example.tomgrocery.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.tomgrocery.model.remote.ApiClient
import com.example.tomgrocery.model.remote.ApiService
import com.example.tomgrocery.model.remote.dto.RegisterData
import com.example.tomgrocery.model.repository.LocalRepository
import com.example.tomgrocery.model.repository.RemoteRepository
import com.example.tomgrocery.model.repository.Repository
import com.example.tomgrocery.databinding.FragmentSignUpBinding
import com.example.tomgrocery.util.MyToast
import com.example.tomgrocery.view.activity.LoginRegisterActivity
import com.example.tomgrocery.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var containerActivity: LoginRegisterActivity
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        containerActivity = requireActivity() as LoginRegisterActivity
        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.signedOn.observe(viewLifecycleOwner) {
            if(it) {
                openLoginScreen()
                MyToast.showToast(requireContext(), "Signed up successfully!")
            }
        }
        viewModel.apiResultMessage.observe(viewLifecycleOwner) {
            if(!it.isEmpty()) {
                MyToast.showToast(requireContext(), it)
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
        binding.signUpBtn.setOnClickListener {
            checkValidation()
        }
        binding.alreadyUser.setOnClickListener {
            openLoginScreen()
        }
    }

    private fun checkValidation() {
        binding.apply {
            if (fullName.text.isEmpty()) {
                fullName.setError("Enter your name")
                fullName.requestFocus()
            } else if (signupEmail.text.isEmpty()) {
                signupEmail.setError("Enter your email")
                signupEmail.requestFocus()
            } else if (mobileNumber.text.isEmpty()) {
                mobileNumber.setError("Entery your mobile number")
                mobileNumber.requestFocus()
            } else if (password.text.isEmpty()) {
                password.setError("Enter your password")
                password.requestFocus()
            } else if (!termsConditions.isChecked) {
                MyToast.showToast(requireContext(), "Accept Terms & Conditions!")
            } else {
                signup()
            }
        }

    }

    private fun signup() {
        val registerData = RegisterData(
            binding.fullName.text.toString(),
            binding.signupEmail.text.toString(),
            binding.password.text.toString(),
            binding.mobileNumber.text.toString()
        )
        viewModel.register(registerData)
    }

    private fun openLoginScreen() {
        containerActivity.replaceLoginFragment()
    }
}