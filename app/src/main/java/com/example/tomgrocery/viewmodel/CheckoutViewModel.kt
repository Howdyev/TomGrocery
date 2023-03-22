package com.example.tomgrocery.viewmodel

import android.util.Log
import androidx.compose.runtime.currentRecomposeScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tomgrocery.model.remote.dto.*
import com.example.tomgrocery.model.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val addressInfo = MutableLiveData<UpdateAddressResponse>()
    val placeOrderResponse = MutableLiveData<PlaceOrderResponse>()
    val placeOrderResult = MutableLiveData<String>("")
    val isProcessing = MutableLiveData(false)
    fun updateUserAddress(updateAddressRequestData: UpdateAddressRequestData) {
        isProcessing.postValue(true)
        val disposable = repository.updateUserAddress(updateAddressRequestData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isProcessing.postValue(false)
                addressInfo.postValue(it)
                Log.i("checkout", "Update user address success!")
            }, {
                Log.i("checkout", "Update user address failed!")
                isProcessing.postValue(false)
            })
        compositeDisposable.add(disposable)
    }

    fun placeOrder(placeOrderRequestData: PlaceOrderRequestData) {
        isProcessing.postValue(true)
        val disposable = repository.placeOrder(placeOrderRequestData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isProcessing.postValue(false)
                placeOrderResponse.postValue(it)
                Log.i("checkout", "Place order success!")
            }, {
                Log.i("checkout", "Place order failed!")
                placeOrderResult.postValue("Place order failed!")
                isProcessing.postValue(false)
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}