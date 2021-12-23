package com.example.farmersecom.features.cart.data.business

import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.farmersecom.features.cart.data.framework.CartDao
import com.example.farmersecom.features.cart.domain.CartItem
import com.example.farmersecom.features.cart.domain.CartRepository
import kotlinx.coroutines.flow.Flow

class CartRepositoryImpl(private val cartDao: CartDao):CartRepository
{



    override suspend fun insertCartItem(cartItem: CartItem?)
    = cartDao.insertCartItem(cartItem)


    override fun getAllCartItems(): Flow<List<CartItem>>
    = cartDao.getAllCartItems()

    override suspend fun deleteAll()
    = cartDao.deleteAll()

    override suspend fun deleteCartItem(cartItem: CartItem?)
    =cartDao.deleteCartItem(cartItem)

    override suspend fun onQuantityChanged(productId: String, quantity: Int)
     = cartDao.onQuantityChanged(productId,quantity)

    override fun getSubTotal() = cartDao.getSubTotal()

    override fun getTotal(): Flow<Int> = cartDao.geTotal()

    override fun getTotalDeliveryCharges(): Flow<Int> = cartDao.getTotalDeliveryCharges()



}