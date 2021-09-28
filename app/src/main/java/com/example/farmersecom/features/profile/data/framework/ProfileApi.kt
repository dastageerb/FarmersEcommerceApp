package com.example.farmersecom.features.profile.data.framework

import retrofit2.Response
import retrofit2.http.GET

interface ProfileApi
{
    @GET("api/profile")
    suspend fun getProfile() : Response<ProfileNetworkEntity>


}