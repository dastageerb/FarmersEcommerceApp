package com.example.farmersecom.features.profile.domain.usecase

import com.example.farmersecom.features.profile.domain.ProfileRepository
import javax.inject.Inject


class GetUserProfileUseCase @Inject constructor (private val profileRepository: ProfileRepository)
{
     suspend fun getUserProfile() = profileRepository.getProfile()
}