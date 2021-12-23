package com.example.farmersecom.features.cart.domain.usecase

import com.example.farmersecom.features.cart.domain.CartItem
import com.example.farmersecom.features.cart.domain.CartRepository
import javax.inject.Inject

class ChangeQuantityUseCase @Inject constructor(private val cartRepository: CartRepository)
{
     suspend fun changeQuantityUseCase(productId:String,quantity:Int) =
          cartRepository.onQuantityChanged(productId,quantity)
}