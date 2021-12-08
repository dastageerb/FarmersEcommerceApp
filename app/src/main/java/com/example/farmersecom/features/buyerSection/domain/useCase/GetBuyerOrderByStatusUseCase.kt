package com.example.farmersecom.features.buyerSection.domain.useCase

import com.example.farmersecom.features.buyerSection.domain.BuyerDashboardRepository
import javax.inject.Inject

class GetBuyerOrderByStatusUseCase  @Inject constructor(private val buyerDashboardRepository: BuyerDashboardRepository)
{
    suspend fun getBuyerOrderByStatus(orderStatus:String) = buyerDashboardRepository.getOrdersByStatus(orderStatus)
}