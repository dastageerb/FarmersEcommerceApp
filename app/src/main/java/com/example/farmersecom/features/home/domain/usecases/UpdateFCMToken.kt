package com.example.farmersecom.features.home.domain.usecases

import com.example.farmersecom.features.home.domain.HomeRepository
import javax.inject.Inject

class UpdateFCMToken @Inject constructor(private val homeRepository: HomeRepository)
{

    suspend fun updateFCMToken(token:String)  = homeRepository.updateFcMToken(token)

} // suspend fun closed