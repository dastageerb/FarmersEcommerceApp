package com.example.farmersecom.features.profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.SharedPrefsHelper
import com.example.farmersecom.features.profile.data.framework.entities.ProfileNetworkEntity
import com.example.farmersecom.features.profile.domain.model.ChangePhotoResponse
import com.example.farmersecom.features.profile.domain.model.UserInfoResponse.UserInfoResponse
import com.example.farmersecom.features.profile.domain.usecase.GetUserFullProfileUseCase
import com.example.farmersecom.features.profile.domain.usecase.GetUserProfileUseCase
import com.example.farmersecom.features.profile.domain.usecase.UploadUserImageUseCase
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getUserProfileUseCase: GetUserProfileUseCase,
                                           private val userImageUseCase: UploadUserImageUseCase,
                                           private val getUserFullProfileUseCase: GetUserFullProfileUseCase,
                                           private val sharedPrefsHelper:SharedPrefsHelper) :ViewModel()
{


    /** Get Profile Use Case*/

    private val _userNetworkEntity:MutableStateFlow<NetworkResource<ProfileNetworkEntity>> = MutableStateFlow(
        NetworkResource.None())
    val userNetworkEntity:StateFlow<NetworkResource<ProfileNetworkEntity>> = _userNetworkEntity


    fun getProfile() = viewModelScope.launch(Dispatchers.IO)
    {
        try
        {
            val response = getUserProfileUseCase.getUserProfile()
            _userNetworkEntity.value = handleProfileResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _userNetworkEntity.value = NetworkResource.Error("Something went wrong")
                else -> _userNetworkEntity.value = NetworkResource.Error("No Internet Connection")
            } // when closed
        } // catch closed
    } //  getProfile closed

    private fun handleProfileResponse(response: Response<ProfileNetworkEntity>): NetworkResource<ProfileNetworkEntity>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    }

    /** Upload Image Use Case*/



        private val _uploadUserImgResponse:MutableStateFlow<NetworkResource<ChangePhotoResponse>> = MutableStateFlow(
            NetworkResource.None())
    val uploadUserImgResponse:StateFlow<NetworkResource<ChangePhotoResponse>> = _uploadUserImgResponse


    fun uploadUserImg(file:MultipartBody.Part) = viewModelScope.launch(Dispatchers.IO)
    {
        _uploadUserImgResponse.value = NetworkResource.Loading()
        try
        {
            val response = userImageUseCase.uploadUserImage(file)
            _uploadUserImgResponse.value = handleUploadImageResponse(response)

        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _uploadUserImgResponse.value = NetworkResource.Error("Something went wrong")
                else -> _uploadUserImgResponse.value = NetworkResource.Error("No Internet Connection")
            } // when closed
        }


    } //  getProfile closed



    private fun handleUploadImageResponse(response: Response<ChangePhotoResponse>): NetworkResource<ChangePhotoResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    } // handleUploadImageResponse closed

    /** Get full user profile **/


    private val _userFullProfileResponse:MutableStateFlow<NetworkResource<UserInfoResponse>> = MutableStateFlow(
        NetworkResource.None())
    val userFullProfileResponse:StateFlow<NetworkResource<UserInfoResponse>> = _userFullProfileResponse


    fun getFullUserProfile() = viewModelScope.launch(Dispatchers.IO)
    {
        try
        {
            val response = getUserFullProfileUseCase.getUserFullProfile()
            _userFullProfileResponse.value = handleFullUserProfileResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _userFullProfileResponse.value = NetworkResource.Error("Something went wrong")
                else -> _userFullProfileResponse.value = NetworkResource.Error("No Internet Connection")
            } // when closed
        } // catch closed
    } //  getProfile closed

    private fun handleFullUserProfileResponse(response: Response<UserInfoResponse>): NetworkResource<UserInfoResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    }

    
    
    
    


    fun getAuthToken() : String? = sharedPrefsHelper.getToken()

    fun clearToken()  = sharedPrefsHelper.clearToken()





} // ProfileViewModel closed