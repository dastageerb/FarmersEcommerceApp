package com.example.farmersecom.features.profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.SharedPrefsHelper
import com.example.farmersecom.features.profile.domain.model.Profile
import com.example.farmersecom.features.profile.domain.usecase.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getUserProfileUseCase: GetUserProfileUseCase,
                                           private val sharedPrefsHelper:SharedPrefsHelper) :ViewModel()
{


    private val _userNetworkEntity:MutableStateFlow<NetworkResource<Profile>> = MutableStateFlow(
        NetworkResource.None())
    val userNetworkEntity:StateFlow<NetworkResource<Profile>> = _userNetworkEntity


    fun getProfile() = viewModelScope.launch(Dispatchers.IO)
    {
        getUserProfileUseCase.getUserProfile().collect()
        {
            _userNetworkEntity.value = it
        }
    }





    fun getAuthToken() :String? = sharedPrefsHelper.getToken()

    fun clearToken()  = sharedPrefsHelper.clearToken()





} // ProfileViewModel closed