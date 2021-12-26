package com.example.farmersecom.features.profile.data.framework

import com.example.farmersecom.features.profile.data.framework.entities.ChangePhotoNetworkEntity
import com.example.farmersecom.features.profile.data.framework.entities.ProfileNetworkEntity
import com.example.farmersecom.features.profile.data.framework.entities.SetUpStoreResponse
import com.example.farmersecom.features.profile.data.framework.entities.SetupStoreData
import com.example.farmersecom.features.profile.domain.model.ChangPasswordRequest
import com.example.farmersecom.features.profile.domain.model.ChangePhotoResponse
import com.example.farmersecom.features.profile.domain.model.UserInfoResponse.UserInfoResponse
import com.example.farmersecom.features.profile.domain.model.editPersonalProfile.EditPersonalInfoEntity
import com.example.farmersecom.features.profile.domain.model.editPersonalProfile.EditPersonalInfoResponse
import com.example.farmersecom.features.storeAdmin.data.framework.entities.responses.NewProductResponse
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface ProfileApi
{
    @GET("api/profile")
    suspend fun getProfile() : Response<ProfileNetworkEntity>

    @GET("api/user-info")
    suspend fun getFullUserProfile() : Response<UserInfoResponse>


    @POST("api/create-store")
    suspend fun setupStore(@Body setupStoreData: SetupStoreData):Response<SetUpStoreResponse>



    @Multipart
    @PUT("api/user-image-upload")
    suspend fun uploadImage(@Part file:MultipartBody.Part ) : Response<ChangePhotoResponse>


    @PUT("api/update-user")
    suspend fun editUserProfile(@Body editPersonalInfoEntity: EditPersonalInfoEntity):Response<EditPersonalInfoResponse>


    @POST("api/change-password")
    suspend fun changePassword(@Body changePassword: ChangPasswordRequest):Response<StatusMsgResponse>



} // profileApi closed