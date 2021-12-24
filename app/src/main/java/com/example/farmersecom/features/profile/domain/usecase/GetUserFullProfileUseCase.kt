package com.example.farmersecom.features.profile.domain.usecase

import com.example.farmersecom.features.profile.domain.ProfileRepository
import javax.inject.Inject


class GetUserFullProfileUseCase @Inject constructor (private val profileRepository: ProfileRepository)
{
     suspend fun getUserFullProfile() = profileRepository.getFullUserProfile()
}