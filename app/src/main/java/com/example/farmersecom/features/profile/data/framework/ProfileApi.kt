package com.example.farmersecom.features.profile.data.framework

import com.example.farmersecom.features.profile.data.framework.entities.ChangePhotoNetworkEntity
import com.example.farmersecom.features.profile.data.framework.entities.ProfileNetworkEntity
import com.example.farmersecom.features.profile.data.framework.entities.SetUpStoreResponse
import com.example.farmersecom.features.profile.data.framework.entities.SetupStoreData
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ProfileApi
{
    @GET("api/profile")
    suspend fun getProfile() : Response<ProfileNetworkEntity>


    @POST("api/create-store")
    suspend fun setupStore(@Body setupStoreData: SetupStoreData):Response<SetUpStoreResponse>


    @Multipart
    @PUT("api/user-image-upload")
    suspend fun uploadImage(@Part file:MultipartBody.Part) : Response<ChangePhotoNetworkEntity>



} // profileApi closed