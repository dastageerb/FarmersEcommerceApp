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
import com.example.farmersecom.features.profile.domain.model.ChangePhotoResponse
import com.example.farmersecom.features.profile.domain.model.Profile
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import com.example.farmersecom.utils.extensionFunctions.handleErros.HandleErrorExtension.handleException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.Response


class ProfileRepoImpl(
    private val profileApi: ProfileApi,
    private val profileNetworkEntityMapper: ProfileNetworkEntityMapper,
    private val changePhotoMapper: ChangePhotoNetworkEntityMapper
) : ProfileRepository
{


    /**   Profile  **/
    override suspend fun getProfile() = flow<NetworkResource<Profile>>
    {
        emit(NetworkResource.Loading())
         try
        {
            val response = profileApi.getProfile()
            emit(handleProfileResponse(response))
        }catch (e:Exception)
        {
            emit(NetworkResource.Error(e.handleException()))
        } //
    } // getProfile closed




    private fun handleProfileResponse(response: Response<ProfileNetworkEntity>): NetworkResource<Profile>
    {
        return when(response.code())
        {
            200,201 ->
            {
                val responseBody = profileNetworkEntityMapper.entityToModel(response.body()!!)
                NetworkResource.Success(responseBody)
            } 400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    } // handle Response closed

    /**   Setup Store  **/


    override suspend fun setupStore(setupStoreData: SetupStoreData): Response<SetUpStoreResponse>
    {
        return profileApi.setupStore(setupStoreData)
    } // setupStore


    /** Upload UserImage */


    override suspend fun uploadUserImage(file: MultipartBody.Part) = flow <NetworkResource<ChangePhotoResponse>>
    {
        emit(NetworkResource.Loading())

        try
            {
                val response = profileApi.uploadImage(file)
                emit(handleUploadImageResponse(response))
            }catch (e:Exception)
            {
                emit(NetworkResource.Error(e.handleException()))
            } //


    } // uploadUserImage closed


    // handleUploadImageResponse

    private fun handleUploadImageResponse(response: Response<ChangePhotoNetworkEntity>): NetworkResource<ChangePhotoResponse>
    {
        return when(response.code())
        {
            200,201 ->
            {
                val responseBody = changePhotoMapper.entityToModel(response.body()!!)
                NetworkResource.Success(responseBody)
            } 400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    }


} // ProfileRepoImpl