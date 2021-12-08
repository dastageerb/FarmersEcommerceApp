package com.example.farmersecom.features.buyerSection.domain.useCase

import com.example.farmersecom.features.buyerSection.domain.BuyerDashboardRepository
import javax.inject.Inject

class GetBuyerFavouritesUseCase @Inject constructor(private val buyerDashboardRepository: BuyerDashboardRepository)
{
    suspend fun getBuyerFavourites() = buyerDashboardRepository.getBuyerFavourites()
}