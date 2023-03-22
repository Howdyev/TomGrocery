package com.example.tomgrocery.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.tomgrocery.model.local.entity.Cart

@Dao
interface CartDao {

    @Insert(onConflict = REPLACE)
    fun addItemToCart(cart: Cart)

    @Delete
    fun deleteItemFromCart(cart: Cart)

    @Query("Delete from Cart")
    fun deleteAllCartItems()

    @Query("Select sum(totalPrice) from Cart")
    fun getCartTotal() : Float

    @Query("select * from Cart")
    fun getCartItems() : MutableList<Cart>

    @Query("select * from Cart WHERE productId = :productId")
    fun getCartItem(productId: String) : Cart

    @Query("SELECT COUNT(productId) FROM Cart")
    fun getCount(): Int
}