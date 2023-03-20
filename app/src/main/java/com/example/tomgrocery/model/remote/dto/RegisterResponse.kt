package com.example.tomgrocery.model.remote.dto

data class RegisterResponse(
    val `data`: User,
    val error: Boolean,
    val message: String
)