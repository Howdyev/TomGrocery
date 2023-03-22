package com.example.tomgrocery.model.remote.dto

data class PlaceOrderRequestData(
    val orderSummary: OrderSummaryToOrder,
    val products: List<ProductToOrder>,
    val shippingAddress: ShippingAddress,
    val user: UserToOrder,
    val userId: String
)