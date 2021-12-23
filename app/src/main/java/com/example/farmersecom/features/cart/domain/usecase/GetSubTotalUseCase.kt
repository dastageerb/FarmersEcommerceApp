package com.example.farmersecom.features.cart.domain.usecase

import com.example.farmersecom.features.cart.domain.CartRepository
import javax.inject.Inject

class GetSubTotalUseCase @Inject constructor(private val cartRepository: CartRepository)
{
     fun getSubTotal() = cartRepository.getSubTotal()
}