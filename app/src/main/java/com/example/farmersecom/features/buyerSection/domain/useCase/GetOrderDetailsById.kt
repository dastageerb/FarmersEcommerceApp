package com.example.farmersecom.features.buyerSection.domain.useCase

import com.example.farmersecom.features.buyerSection.domain.BuyerDashboardRepository
import javax.inject.Inject

class GetOrderDetailsById @Inject constructor(private val buyerDashboardRepository: BuyerDashboardRepository)
{
    suspend fun getOrderDetailsById(id:String) = buyerDashboardRepository.getOrderDetailsById(id)
}