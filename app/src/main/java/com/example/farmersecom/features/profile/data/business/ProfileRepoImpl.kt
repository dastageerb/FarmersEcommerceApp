package com.example.farmersecom.features.profile.data.business

import com.example.farmersecom.features.profile.data.framework.ProfileApi
import com.example.farmersecom.features.profile.data.framework.ProfileNetworkEntity
import com.example.farmersecom.features.profile.domain.ProfileRepository
import retrofit2.Response


class ProfileRepoImpl (private val profileApi: ProfileApi) : ProfileRepository
{

    override suspend fun profileResponse(): Response<ProfileNetworkEntity>
    {
        return profileApi.getProfile()
    }
}