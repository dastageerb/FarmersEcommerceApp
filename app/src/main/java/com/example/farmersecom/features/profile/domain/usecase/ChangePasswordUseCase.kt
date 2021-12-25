package com.example.farmersecom.features.profile.domain.usecase

import com.example.farmersecom.features.profile.domain.ProfileRepository
import javax.inject.Inject


class ChangePasswordUseCase @Inject constructor (private val profileRepository: ProfileRepository)
{
     suspend fun changePasswordUseCase(oldPassword:String,newPassword:String)
     = profileRepository.changePassword(oldPassword,newPassword)
}