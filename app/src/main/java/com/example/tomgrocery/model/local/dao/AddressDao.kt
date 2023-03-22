package com.example.tomgrocery.model.local.dao

import androidx.room.*
import com.example.tomgrocery.model.remote.dto.Address

@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAddress(address: Address)

    @Delete
    fun deleteAddress(address: Address)

    @Update
    fun updateAddress(address: Address)

    @Query("select * from Address")
    fun getAllAddresses(): List<Address>
}