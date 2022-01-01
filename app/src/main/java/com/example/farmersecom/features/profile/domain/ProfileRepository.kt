package com.example.farmersecom.features.profile.domain

import com.example.farmersecom.features.profile.data.framework.entities.ChangePhotoNetworkEntity
import com.example.farmersecom.features.profile.data.framework.entities.ProfileNetworkEntity
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.features.profile.data.framework.entities.SetUpStoreResponse
import com.example.farmersecom.features.profile.data.framework.entities.SetupStoreData
import com.example.farmersecom.features.profile.domain.model.ChangePhotoResponse
import com.example.farmersecom.features.profile.domain.model.Profile
import com.example.farmersecom.features.profile.domain.model.UserInfoResponse.UserInfoResponse
import com.example.farmersecom.features.profile.domain.model.editPersonalProfile.EditPersonalInfoEntity
import com.example.farmersecom.features.profile.domain.model.editPersonalProfile.EditPersonalInfoResponse
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ProfileRepository
{


    suspend fun getProfile() : Response<ProfileNetworkEntity>

    suspend fun setupStore(setupStoreData: SetupStoreData):Response<SetUpStoreResponse>

    suspend fun uploadUserImage(file:MultipartBody.Part):Response<ChangePhotoResponse>

    suspend fun getFullUserProfile() : Response<UserInfoResponse>

    suspend fun editUserProfile( editPersonalInfoEntity: EditPersonalInfoEntity):Response<EditPersonalInfoResponse>

    suspend fun changePassword(oldPassword:String,newPassword:String):Response<StatusMsgResponse>

    suspend fun updateFcMToken(token:String):Response<StatusMsgResponse>

}