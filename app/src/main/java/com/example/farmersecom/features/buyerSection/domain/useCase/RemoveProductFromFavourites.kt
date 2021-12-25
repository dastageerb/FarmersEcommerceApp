package com.example.farmersecom.features.buyerSection.domain.useCase

import com.example.farmersecom.features.buyerSection.domain.BuyerDashboardRepository
import javax.inject.Inject

class RemoveProductFromFavourites @Inject constructor(private val buyerDashboardRepository: BuyerDashboardRepository)
{
    suspend fun removeProductFromFavourites(productId:String) = buyerDashboardRepository.removeProductFromFavourites(productId)
}