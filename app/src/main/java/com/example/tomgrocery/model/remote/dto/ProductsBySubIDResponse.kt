package com.example.tomgrocery.model.remote.dto

data class ProductsBySubIDResponse(
    val count: Int,
    val `data`: List<Product>,
    val error: Boolean
)