package com.example.tomgrocery.model.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Address(
    @PrimaryKey(autoGenerate = true) val index: Int,
    val __v: Int,
    val _id: String,
    val state: String?,
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String,
    val userId: String
)