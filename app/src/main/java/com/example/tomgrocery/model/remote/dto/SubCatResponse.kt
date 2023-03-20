package com.example.tomgrocery.model.remote.dto

data class SubCatResponse(
    val count: Int,
    val data: List<SubCategory>,
    val error: Boolean
)