package com.example.farmersecom.features.cart.domain.usecase

import com.example.farmersecom.features.cart.domain.CartItem
import com.example.farmersecom.features.cart.domain.CartRepository
import javax.inject.Inject

class InserCartItemUseCase @Inject constructor(private val cartRepository: CartRepository)
{
     suspend fun insertCartItem(cartItem: CartItem) = cartRepository.insertCartItem(cartItem)
}