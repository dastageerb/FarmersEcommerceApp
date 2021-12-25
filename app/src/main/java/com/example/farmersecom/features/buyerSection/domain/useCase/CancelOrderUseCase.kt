package com.example.farmersecom.features.buyerSection.domain.useCase

import com.example.farmersecom.features.buyerSection.domain.BuyerDashboardRepository
import javax.inject.Inject

class CancelOrderUseCase @Inject constructor(private val buyerDashboardRepository: BuyerDashboardRepository)
{
    suspend fun cancelOrder(orderId:String) = buyerDashboardRepository.cancelOrder(orderId)
}