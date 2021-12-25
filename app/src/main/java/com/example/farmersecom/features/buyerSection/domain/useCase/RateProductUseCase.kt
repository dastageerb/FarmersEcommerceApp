package com.example.farmersecom.features.buyerSection.domain.useCase

import com.example.farmersecom.features.buyerSection.domain.BuyerDashboardRepository
import javax.inject.Inject

class RateProductUseCase @Inject constructor(private val buyerDashboardRepository: BuyerDashboardRepository)
{
    suspend fun rateProduct(productId:String,rating:Float) = buyerDashboardRepository.rateProduct(productId,rating)
}