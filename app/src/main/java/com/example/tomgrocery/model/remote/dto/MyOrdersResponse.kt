package com.example.tomgrocery.model.remote.dto

data class MyOrdersResponse(
    val count: Int,
    val `data`: List<MyOrdersData>,
    val error: Boolean
)