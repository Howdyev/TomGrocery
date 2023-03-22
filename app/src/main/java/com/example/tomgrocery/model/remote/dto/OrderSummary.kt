package com.example.tomgrocery.model.remote.dto

data class OrderSummary(
    val _id: String,
    val deliveryCharges: Int,
    val discount: Int,
    val ourPrice: Int,
    val totalAmount: Float
)

data class OrderSummaryToOrder(
    val deliveryCharges: Int,
    val discount: Int,
    val ourPrice: Int,
    val totalAmount: Float
)