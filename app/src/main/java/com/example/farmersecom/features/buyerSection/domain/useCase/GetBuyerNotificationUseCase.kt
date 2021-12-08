package com.example.farmersecom.features.buyerSection.domain.useCase

import com.example.farmersecom.features.buyerSection.domain.BuyerDashboardRepository
import javax.inject.Inject

class GetBuyerNotificationUseCase  @Inject constructor(private val buyerDashboardRepository: BuyerDashboardRepository)
{
    suspend fun getBuyerNotifications() = buyerDashboardRepository.getBuyerNotifications()
}