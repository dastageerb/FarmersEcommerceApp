package com.example.farmersecom.features.orderDetails.domain.usecase

import com.example.farmersecom.features.orderDetails.domain.PlaceOrderRepository
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(private val placeOrderRepository: PlaceOrderRepository)
{
    suspend fun placeOrder() = placeOrderRepository.placeOrder()
}