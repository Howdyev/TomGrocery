package com.example.tomgrocery.model.remote.dto


data class LoginResponse(
    val token: String,
    val user: User
)