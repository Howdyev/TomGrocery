package com.example.tomgrocery.model.repository

import com.example.tomgrocery.model.remote.dto.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class Repository(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
) {
    private lateinit var disposable: Disposable

    fun loginUser(loginData: LoginData) : Single<LoginResponse>{
        return remoteRepository.loginUser(loginData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun registerUser(registerData: RegisterData): Single<SignupResponse> {
        return remoteRepository.registerUser(registerData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateUserAddress(updateUserRequestData: UpdateAddressRequestData) : Single<UpdateAddressResponse>{
        return remoteRepository.updateUserAddress(updateUserRequestData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun placeOrder(placeOrderRequestData: PlaceOrderRequestData) : Single<PlaceOrderResponse>{
        return remoteRepository.placeOrder(placeOrderRequestData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun myOrders(userId: String) : Single<MyOrdersResponse>{
        return remoteRepository.myOrders(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCategories() : Single<CategoryResponse> {
        return remoteRepository.getCategories()
    }

    fun searchProduct(search:String) : Single<SearchResponse> {
        return remoteRepository.searchProduct(search)
    }

    fun getSubCategoryData(catId: String) : Single<SubCatResponse> {
        return remoteRepository.getSubCategoryData(catId)
    }

    fun getProductsBySubId(subId:String) :Single<ProductsBySubIDResponse> {
        return remoteRepository.getProductsBySubId(subId)
    }
}