package com.example.tomgrocery.model.remote.dto

data class SearchResponse(
    val count: Int,
    val data: List<Product>,
    val error: Boolean
)