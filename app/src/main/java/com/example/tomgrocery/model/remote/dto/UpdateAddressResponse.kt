package com.example.tomgrocery.model.remote.dto

data class UpdateAddressResponse(
    val `data`: Address,
    val error: Boolean,
    val message: String
)