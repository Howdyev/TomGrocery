package com.example.tomgrocery.util

import android.content.Context
import android.widget.Toast

object MyToast {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}