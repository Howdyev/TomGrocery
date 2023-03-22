package com.example.tomgrocery.model.repository

import com.example.tomgrocery.model.remote.ApiService
import com.example.tomgrocery.model.remote.dto.*
import io.reactivex.Single

class RemoteRepository(private val apiService: ApiService) {
    fun loginUser(loginData: LoginData) : Single<LoginResponse>{
        return apiService.loginUser(loginData)
    }

    fun registerUser(registerData: RegisterData) : Single<SignupResponse> {
        return apiService.registerUser(registerData)
    }

    fun updateUserAddress(updateAddressRequestData: UpdateAddressRequestData) : Single<UpdateAddressResponse>{
        return apiService.updateUserAddress(updateAddressRequestData)
    }

    fun placeOrder(placeOrderRequestData: PlaceOrderRequestData) : Single<PlaceOrderResponse>{
        return apiService.placeOrder(placeOrderRequestData)
    }

    fun myOrders(userId: String) : Single<MyOrdersResponse> {
        return apiService.myOrders(userId)
    }

    fun getCategories() : Single<CategoryResponse> {
        return apiService.getCategories()
    }

    fun searchProduct(search:String) : Single<SearchResponse> {
        return apiService.searchProduct(search)
    }

    fun getSubCategoryData(subId: String) : Single<SubCatResponse> {
        return apiService.getSubCategoryData(subId)
    }

    fun getProductsBySubId(subId:String) : Single<ProductsBySubIDResponse> {
        return apiService.getProductsBySubId(subId)
    }
}