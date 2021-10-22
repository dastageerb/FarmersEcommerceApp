package com.example.farmersecom.features.profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.SharedPrefsHelper
import com.example.farmersecom.features.profile.domain.model.ChangePhotoResponse
import com.example.farmersecom.features.profile.domain.model.Profile
import com.example.farmersecom.features.profile.domain.usecase.GetUserProfileUseCase
import com.example.farmersecom.features.profile.domain.usecase.UploadUserImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getUserProfileUseCase: GetUserProfileUseCase,
                                           private val userImageUseCase: UploadUserImageUseCase,
                                           private val sharedPrefsHelper:SharedPrefsHelper) :ViewModel()
{


    /** Get Profile Use Case*/

    private val _userNetworkEntity:MutableStateFlow<NetworkResource<Profile>> = MutableStateFlow(
        NetworkResource.None())
    val userNetworkEntity:StateFlow<NetworkResource<Profile>> = _userNetworkEntity


    fun getProfile() = viewModelScope.launch(Dispatchers.IO)
    {
        getUserProfileUseCase.getUserProfile().collect()
        {
            _userNetworkEntity.value = it
        } // getProfileUseCase closed
    } //  getProfile closed

        /** Upload Image Use Case*/



        private val _uploadUserImgResponse:MutableStateFlow<NetworkResource<ChangePhotoResponse>> = MutableStateFlow(
            NetworkResource.None())
    val uploadUserImgResponse:StateFlow<NetworkResource<ChangePhotoResponse>> = _uploadUserImgResponse


    fun uploadUserImg(file:MultipartBody.Part) = viewModelScope.launch(Dispatchers.IO)
    {
        userImageUseCase.uploadUserImage(file).collect()
        {
            _uploadUserImgResponse.value = it
        } // getProfileUseCase closed
    } //  getProfile closed







    fun getAuthToken() :String? = sharedPrefsHelper.getToken()

    fun clearToken()  = sharedPrefsHelper.clearToken()





} // ProfileViewModel closed