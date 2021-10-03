package com.example.farmersecom.features.profile.domain.usecase

import com.example.farmersecom.features.profile.data.framework.entities.SetupStoreData
import com.example.farmersecom.features.profile.domain.ProfileRepository
import javax.inject.Inject

class SetupStoreUseCase @Inject constructor(private val profileRepository: ProfileRepository)
{
    suspend fun setupStore(setupStoreData: SetupStoreData) = profileRepository.setupStore(setupStoreData)
}
