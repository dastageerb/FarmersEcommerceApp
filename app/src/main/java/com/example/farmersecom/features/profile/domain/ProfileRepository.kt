package com.example.farmersecom.features.profile.domain

import com.example.akhbar.utils.NetworkResource
import com.example.farmersecom.features.profile.data.framework.entities.SetUpStoreResponse
import com.example.farmersecom.features.profile.data.framework.entities.SetupStoreData
import com.example.farmersecom.features.profile.domain.model.Profile
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ProfileRepository
{


    suspend fun getProfile() : Flow<NetworkResource<Profile>>

    suspend fun setupStore(setupStoreData: SetupStoreData):Response<SetUpStoreResponse>
}