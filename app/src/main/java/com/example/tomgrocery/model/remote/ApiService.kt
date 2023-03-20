package com.example.tomgrocery.model.remote

import com.example.tomgrocery.constants.Constants.CATEGORY_END_POINT
import com.example.tomgrocery.constants.Constants.LOGIN_END_POINT
import com.example.tomgrocery.constants.Constants.PRODUCTS_BY_SUB_ID_END_POINT
import com.example.tomgrocery.constants.Constants.REGISTRATION_END_POINT
import com.example.tomgrocery.constants.Constants.SEARCH_END_POINT
import com.example.tomgrocery.constants.Constants.SUBCATEGORY_END_POINT
import com.example.tomgrocery.model.remote.dto.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST(LOGIN_END_POINT)
    fun loginUser(@Body loginData: LoginData) : Single<LoginResponse>

    @POST(REGISTRATION_END_POINT)
    fun registerUser(@Body registerData: RegisterData) : Single<SignupResponse>

    @GET(CATEGORY_END_POINT)
    fun getCategories() : Single<CategoryResponse>

    @GET("$SEARCH_END_POINT{name}")
    fun searchProduct(@Path("name") search: String) : Single<SearchResponse>

    @GET("$SUBCATEGORY_END_POINT{subId}")
    fun getSubCategoryData(@Path("subId") subId: String) : Single<SubCatResponse>

    @GET("$PRODUCTS_BY_SUB_ID_END_POINT{subId}")
    fun getProductsBySubId(@Path("subId") subId: String) : Single<ProductsBySubIDResponse>
}