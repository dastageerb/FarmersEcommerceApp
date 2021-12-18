package com.example.farmersecom.features.home.domain.usecases

import com.example.farmersecom.features.home.domain.HomeRepository
import javax.inject.Inject

class GetMoreSliderItemsUseCase @Inject constructor(private val homeRepository: HomeRepository)
{
    suspend fun getMoreSliderItems(query:String) = homeRepository.moreSliderItems(query)
}