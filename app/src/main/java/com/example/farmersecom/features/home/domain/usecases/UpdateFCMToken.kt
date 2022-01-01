package com.example.farmersecom.features.home.domain.usecases

import com.example.farmersecom.features.home.domain.HomeRepository
import com.example.farmersecom.features.profile.domain.ProfileRepository
import javax.inject.Inject

class UpdateFCMToken @Inject constructor(private val profileRepository: ProfileRepository)
{

    suspend fun updateFCMToken(token:String)  = profileRepository.updateFcMToken(token)

} // suspend fun closed