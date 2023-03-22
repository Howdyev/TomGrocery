package com.example.tomgrocery.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tomgrocery.model.remote.dto.LoginData
import com.example.tomgrocery.model.remote.dto.LoginResponse
import com.example.tomgrocery.model.remote.dto.RegisterData
import com.example.tomgrocery.model.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    val userInfo = MutableLiveData<LoginResponse>()
    val isProcessing = MutableLiveData(false)
    val signedOn = MutableLiveData( false)
    val apiResultMessage = MutableLiveData<String>("")
    private val compositeDisposable = CompositeDisposable()
    fun login(loginData: LoginData) {
        isProcessing.postValue(true)
        val disposable = repository.loginUser(loginData)
            .subscribe(
                {
                    Log.i("login", "login success")
                    userInfo.postValue(it)
                    isProcessing.postValue(false)
                    apiResultMessage.postValue("Login success!")
                },
                {
                    Log.i("login", "login failed!")
                    isProcessing.postValue(false)
                    apiResultMessage.postValue("Login failed!")
                }
            )
        compositeDisposable.add(disposable)
    }

    fun register(registerData: RegisterData) {
        isProcessing.postValue(true)
        val disposable = repository.registerUser(registerData)
            .subscribe({
                if(!it.error) {
                    Log.i("Signup", "Signup success!")
                    signedOn.postValue(true)
                    apiResultMessage.postValue("Signup success!")
                } else {
                    Log.i("Signup", "Signup with Error!")
                    apiResultMessage.postValue(it.message)
                }
                isProcessing.postValue(false)
            },{
                Log.i("Signup", "Signup Failed!")
                apiResultMessage.postValue("Signup api call failed!")
                isProcessing.postValue(false)
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}