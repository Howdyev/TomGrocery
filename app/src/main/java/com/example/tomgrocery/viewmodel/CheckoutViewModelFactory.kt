package com.example.tomgrocery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tomgrocery.model.repository.Repository
import javax.inject.Inject

class CheckoutViewModelFactory @Inject constructor(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CheckoutViewModel(repository) as T
    }
}