package com.example.farmersecom.features.home.domain.usecases

import com.example.farmersecom.features.home.domain.HomeRepository
import javax.inject.Inject

class GetSliderItemsUseCase @Inject constructor(private val homeRepository: HomeRepository)
{
    suspend fun getSliderItems() = homeRepository.getSliderItems()
}