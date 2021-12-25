package com.example.farmersecom.features.profile.domain.usecase

import com.example.farmersecom.features.profile.domain.ProfileRepository
import com.example.farmersecom.features.profile.domain.model.editPersonalProfile.EditPersonalInfoEntity
import javax.inject.Inject


class EditPersonalInfoUseCase @Inject constructor (private val profileRepository: ProfileRepository)
{
     suspend fun editPersonalInfo(editPersonalInfoEntity: EditPersonalInfoEntity)
     = profileRepository.editUserProfile(editPersonalInfoEntity)
}