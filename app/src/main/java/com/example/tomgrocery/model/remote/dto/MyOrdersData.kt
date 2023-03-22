package com.example.tomgrocery.model.remote.dto

data class MyOrdersData(
    val __v: Int,
    val _id: String,
    val date: String,
    val orderSummary: OrderSummary,
    val products: List<Product>,
    val shippingAddress: ShippingAddress,
    val user: User,
    val userId: String
)