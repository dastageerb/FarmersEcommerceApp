package com.example.farmersecom.features.profile.domain.usecase

import com.example.farmersecom.features.profile.domain.ProfileRepository
import okhttp3.MultipartBody
import javax.inject.Inject


class UploadUserImageUseCase @Inject constructor (private val profileRepository: ProfileRepository)
{
     suspend fun uploadUserImage(file:MultipartBody.Part) = profileRepository.uploadUserImage(file)
}