package com.example.farmersecom.features.profile.data.business


import com.example.farmersecom.features.profile.data.framework.ChangePhotoNetworkEntityMapper
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.features.profile.data.framework.ProfileNetworkEntityMapper
import com.example.farmersecom.features.profile.data.framework.ProfileApi
import com.example.farmersecom.features.profile.data.framework.entities.ChangePhotoNetworkEntity
import com.example.farmersecom.features.profile.data.framework.entities.ProfileNetworkEntity
import com.example.farmersecom.features.profile.data.framework.entities.SetUpStoreResponse
import com.example.farmersecom.features.profile.data.framework.entities.SetupStoreData
import com.example.farmersecom.features.profile.domain.ProfileRepository
import com.example.farmersecom.features.profile.domain.model.ChangPasswordRequest
import com.example.farmersecom.features.profile.domain.model.ChangePhotoResponse
import com.example.farmersecom.features.profile.domain.model.Profile
import com.example.farmersecom.features.profile.domain.model.UserInfoResponse.UserInfoResponse
import com.example.farmersecom.features.profile.domain.model.editPersonalProfile.EditPersonalInfoEntity
import com.example.farmersecom.features.profile.domain.model.editPersonalProfile.EditPersonalInfoResponse
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import com.example.farmersecom.utils.extensionFunctions.handleErros.HandleErrorExtension.handleException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Response


class ProfileRepoImpl(
    private val profileApi: ProfileApi,
    private val profileNetworkEntityMapper: ProfileNetworkEntityMapper,
    private val changePhotoMapper: ChangePhotoNetworkEntityMapper
) : ProfileRepository
{


    /**   Profile  **/
    override suspend fun getProfile() = profileApi.getProfile()



    override suspend fun setupStore(setupStoreData: SetupStoreData): Response<SetUpStoreResponse>
    {
        return profileApi.setupStore(setupStoreData)
    } // setupStore


    /** Upload UserImage */


    override suspend fun uploadUserImage(file: MultipartBody.Part) = profileApi.uploadImage(file)




    // handleUploadImageResponse



     /// Get Ful UserProfile

    override suspend fun getFullUserProfile(): Response<UserInfoResponse>
    = profileApi.getFullUserProfile()

    override suspend fun editUserProfile(editPersonalInfoEntity: EditPersonalInfoEntity): Response<EditPersonalInfoResponse>
    =profileApi.editUserProfile(editPersonalInfoEntity)

    override suspend fun changePassword(oldPassword: String, newPassword: String): Response<StatusMsgResponse>
    {
        return  profileApi.changePassword(ChangPasswordRequest(newPassword,oldPassword))
    }


} // ProfileRepoImpl