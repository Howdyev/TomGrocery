package com.example.tomgrocery.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tomgrocery.model.local.dao.AddressDao
import com.example.tomgrocery.model.local.dao.CartDao
import com.example.tomgrocery.model.local.dao.PaymentDao
import com.example.tomgrocery.model.local.entity.Cart
import com.example.tomgrocery.model.local.entity.Payment
import com.example.tomgrocery.model.remote.dto.Address

@Database(
    entities = [Cart::class, Address::class, Payment::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCartDao(): CartDao
    abstract fun getAdressDao(): AddressDao
    abstract fun getPaymentDao(): PaymentDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appDB"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
    }
}