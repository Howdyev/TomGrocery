package com.example.tomgrocery.model.remote.dto

data class PlaceOrderResponse(
    val `data`: PlaceOrderData,
    val error: Boolean,
    val message: String
)