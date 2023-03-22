package com.example.tomgrocery.viewmodel

import android.util.Log
import androidx.compose.runtime.currentRecomposeScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tomgrocery.model.remote.dto.Category
import com.example.tomgrocery.model.remote.dto.MyOrdersResponse
import com.example.tomgrocery.model.remote.dto.Product
import com.example.tomgrocery.model.remote.dto.SubCategory
import com.example.tomgrocery.model.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DashboardViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val isProcessing = MutableLiveData(false)
    var categoryList = MutableLiveData<List<Category>>()
    var searchList = MutableLiveData<List<Product>>()
    var subCategories = MutableLiveData<List<SubCategory>>()
    var size = MutableLiveData<Int>()
    var search = MutableLiveData<String>()
    var products = MutableLiveData<List<Product>>()

    var myOrdersResponse = MutableLiveData<MyOrdersResponse>()

    private var compositeDisposable = CompositeDisposable()

    fun getCategories() {
        isProcessing.postValue(true)
        val disposable = repository.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                categoryList.value = it.data
                isProcessing.postValue(false)
            }, {
                Log.i("category", "category failed")
                isProcessing.postValue(false)
            })
        compositeDisposable.add(disposable)
    }

    fun searchProduct(search: String) {
        val disposable = repository.searchProduct(search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                searchList.value = it.data
                isProcessing.postValue(false)
            }, {
                Log.i("search", "search failed")
            })
        compositeDisposable.add(disposable)
    }

    fun getSubCategory() {
        isProcessing.postValue(true)
        val disposable = repository.getSubCategoryData(staticCategoryId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                subCategories.value = it.data
                isProcessing.postValue(false)
            }, {
                Log.i("subcat", "subcat failed")
                isProcessing.postValue(false)
            })
        compositeDisposable.add(disposable)
    }

    fun getProductsBySubId(subCatId: Int) {
        isProcessing.postValue(true)
        products.postValue(listOf())
        val disposable = repository.getProductsBySubId(subCatId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                products.postValue(it.data)
                isProcessing.postValue(false)
            }, {
                Log.i("products", "products failed")
                isProcessing.postValue(false)
            })
        compositeDisposable.add(disposable)
    }

    fun myOrders(userId: String) {
        isProcessing.postValue(true)
        val disposable = repository.myOrders(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                myOrdersResponse.postValue(it)
                isProcessing.postValue(false)
            },{
                Log.i("myorders", "my orders failed!")
                isProcessing.postValue(false)
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    companion object {
        var staticCategoryId = 1
    }
}