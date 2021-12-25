package com.example.farmersecom.features.buyerSection.domain.useCase

import com.example.farmersecom.features.buyerSection.domain.BuyerDashboardRepository
import javax.inject.Inject

class AddProductToFavouritesUseCase @Inject constructor(private val buyerDashboardRepository: BuyerDashboardRepository)
{
    suspend fun addProductToFavourites(productId:String) = buyerDashboardRepository.addProductToFavourites(productId)
}