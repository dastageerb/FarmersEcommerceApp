package com.example.farmersecom.features.profile.data.framework

import com.example.farmersecom.features.profile.data.framework.entities.ProfileNetworkEntity
import com.example.farmersecom.features.profile.data.framework.entities.SetUpStoreResponse
import com.example.farmersecom.features.profile.data.framework.entities.SetupStoreData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProfileApi
{
    @GET("api/profile")
    suspend fun getProfile() : Response<ProfileNetworkEntity>


    @POST("api/create-store")
    suspend fun setupStore(@Body setupStoreData: SetupStoreData):Response<SetUpStoreResponse>



} // profileApi closed