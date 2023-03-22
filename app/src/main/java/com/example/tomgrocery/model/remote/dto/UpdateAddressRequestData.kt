package com.example.tomgrocery.model.remote.dto

data class UpdateAddressRequestData(
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String,
    val userId: String
)