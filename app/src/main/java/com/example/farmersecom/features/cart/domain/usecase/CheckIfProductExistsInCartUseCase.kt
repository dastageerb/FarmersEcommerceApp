package com.example.farmersecom.features.cart.domain.usecase

import com.example.farmersecom.features.cart.domain.CartItem
import com.example.farmersecom.features.cart.domain.CartRepository
import javax.inject.Inject

class CheckIfProductExistsInCartUseCase @Inject constructor(private val cartRepository: CartRepository)
{
     fun exists(productId:String) =
          cartRepository.exists(productId)
}