package com.example.farmersecom.features.cart.domain.usecase

import com.example.farmersecom.features.cart.domain.CartRepository
import javax.inject.Inject

class GetTotalDeliveryChargesUseCase @Inject constructor(private val cartRepository: CartRepository)
{
     fun getTotalDeliveryCharges() = cartRepository.getTotalDeliveryCharges()
}