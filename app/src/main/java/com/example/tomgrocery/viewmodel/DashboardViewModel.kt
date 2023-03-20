package com.example.tomgrocery.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tomgrocery.model.remote.dto.Category
import com.example.tomgrocery.model.remote.dto.Product
import com.example.tomgrocery.model.remote.dto.SubCategory
import com.example.tomgrocery.model.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
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

    lateinit var disposable: Disposable

    fun getCategories() {
        isProcessing.postValue(true)
        disposable = repository.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                categoryList.value = it.data
                isProcessing.postValue(false)
            }, {
                Log.i("category", "category failed")
                isProcessing.postValue(false)
            })
    }

    fun searchProduct(search: String) {
        disposable = repository.searchProduct(search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                searchList.value = it.data
                isProcessing.postValue(false)
            }, {
                Log.i("search", "search failed")
            })
    }

    fun getSubCategory() {
        isProcessing.postValue(true)
        disposable = repository.getSubCategoryData(staticCategoryId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                subCategories.value = it.data
                isProcessing.postValue(false)
            }, {
                Log.i("subcat", "subcat failed")
                isProcessing.postValue(false)
            })
    }

    fun getProductsBySubId(subCatId: Int) {
        isProcessing.postValue(true)
        products.postValue(listOf())
        disposable = repository.getProductsBySubId(subCatId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                products.postValue(it.data)
                isProcessing.postValue(false)
            }, {
                Log.i("products", "products failed")
                isProcessing.postValue(false)
            })
    }

    companion object {
        var staticCategoryId = 1
    }
}