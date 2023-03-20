package com.example.tomgrocery.model.remote.dto

data class SignupResponse(
    val error: Boolean,
    val message: String,
    val data: User
)