package com.example.farmersecom.features.profile.domain

import com.example.farmersecom.features.profile.data.framework.ProfileNetworkEntity
import retrofit2.Response

interface ProfileRepository
{


    suspend fun profileResponse() : Response<ProfileNetworkEntity>


}