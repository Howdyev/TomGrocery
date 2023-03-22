package com.example.tomgrocery.model.remote.dto

data class ProductToOrder(
    val price: Float,
    val productName: String,
    val quantity: Int
)