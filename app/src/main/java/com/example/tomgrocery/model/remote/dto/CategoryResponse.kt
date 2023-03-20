package com.example.tomgrocery.model.remote.dto

import com.example.tomgrocery.model.remote.dto.Category

data class CategoryResponse(
    val count: Int,
    val `data`: List<Category>,
    val error: Boolean
)