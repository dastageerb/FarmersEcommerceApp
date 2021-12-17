package com.example.farmersecom.features.home.domain.usecases

import com.example.farmersecom.features.home.domain.HomeRepository
import javax.inject.Inject

class GetHomeLatestItemsUseCase @Inject constructor(private val homeRepository: HomeRepository)
{
    suspend fun getHomeLatestItemsUseCase()  = homeRepository.getHomeLatestItems()
} // suspend fun closed