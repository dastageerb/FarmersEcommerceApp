package com.example.farmersecom.features.cart.domain.usecase

import com.example.farmersecom.features.cart.domain.CartItem
import com.example.farmersecom.features.cart.domain.CartRepository
import javax.inject.Inject

class DeleteCartItemUseCase @Inject constructor(private val cartRepository: CartRepository)
{
     suspend fun deleteCartItem(cartItem: CartItem) = cartRepository.deleteCartItem(cartItem)
}