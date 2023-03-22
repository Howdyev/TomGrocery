package com.example.tomgrocery.model.local.dao

import androidx.room.*
import com.example.tomgrocery.model.local.entity.Payment

@Dao
interface PaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPayment(payment: Payment)

    @Delete
    fun deletePayment(payment: Payment)

    @Update
    fun updatePayment(payment: Payment)

    @Query("delete from Payment")
    fun deleteAllPayments()

    @Query("select * from Payment")
    fun getAllPayments(): List<Payment>
}