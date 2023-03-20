package com.example.tomgrocery.model.remote.dto

data class RegisterData(
    val firstName: String,
    val email: String,
    val password: String,
    val mobile: String
)