package com.example.tomgrocery.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cart(
    @PrimaryKey(autoGenerate = true) val index: Int,
    val productName: String,
    val productId: String,
    val quantity: Int,
    val price: Float,
    val totalPrice: Float,
    val productImg: String
)
