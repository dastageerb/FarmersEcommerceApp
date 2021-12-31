package com.example.farmersecom.features.cart.domain

import androidx.paging.PagingData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

interface CartRepository
{

    suspend fun insertCartItem(cartItem: CartItem?)

    fun getAllCartItems():Flow<List<CartItem>>

    suspend fun deleteAll()

    suspend fun deleteCartItem(cartItem: CartItem?)

    suspend fun onQuantityChanged(productId:String,quantity:Int)

    fun getSubTotal():Flow<Int>

    fun getTotal():Flow<Int>

    fun getTotalDeliveryCharges():Flow<Int>

    fun exists(id: String): Boolean


}