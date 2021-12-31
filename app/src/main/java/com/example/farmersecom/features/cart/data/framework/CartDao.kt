package com.example.farmersecom.features.cart.data.framework

import androidx.room.*
import com.example.farmersecom.features.cart.domain.CartItem
import kotlinx.coroutines.flow.Flow


@Dao
interface CartDao
{


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem?)



    @Query("SELECT * FROM CART_TABLE order by date asc")
    fun getAllCartItems():Flow<List<CartItem>>


    @Query("DELETE FROM CART_TABLE ")
    suspend fun deleteAll()


    @Delete
    suspend fun deleteCartItem(cartItem: CartItem?)


    @Query("UPDATE CART_TABLE  SET productQuantity =:quantity  WHERE productId =:productId")
    suspend fun onQuantityChanged(productId:String,quantity:Int)


    @Query("SELECT SUM(productPrice*productQuantity) From cart_table")
    fun getSubTotal():Flow<Int>

    @Query("SELECT SUM(productPrice*productQuantity+deliveryCharges) From cart_table")
    fun geTotal():Flow<Int>

    @Query("SELECT SUM(deliveryCharges) from cart_table ")
    fun getTotalDeliveryCharges():Flow<Int>

    @Query("SELECT EXISTS (SELECT 1 FROM CART_TABLE WHERE productId = :id)")
    fun exists(id: String): Boolean



}