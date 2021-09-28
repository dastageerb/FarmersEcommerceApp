package com.example.farmersecom.features.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akhbar.utils.NetworkResource
import com.example.farmersecom.SharedPrefsHelper
import com.example.farmersecom.features.profile.data.framework.ProfileNetworkEntity
import com.example.farmersecom.features.profile.domain.usecase.GetUserProfile
import com.example.farmersecom.utils.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val dataStoreRepo: DataStoreRepo,
                                           private val getprofile: GetUserProfile,
                                           private val sharedPrefsHelper:SharedPrefsHelper) :ViewModel()
{


    private val _userResponse:MutableStateFlow<NetworkResource<ProfileNetworkEntity>> = MutableStateFlow(NetworkResource.None())
    val userResponse:StateFlow<NetworkResource<ProfileNetworkEntity>> = _userResponse

    fun clearToken() = viewModelScope.launch(Dispatchers.IO) { dataStoreRepo.clearToken() }

    fun getUser() = viewModelScope.launch(Dispatchers.IO)
    {
        try
        {
            val response = getprofile.getUserProfile()
            Timber.tag(TAG).d("response body:"+response.body())
            Timber.tag(TAG).d("response code:"+response.code())
            _userResponse.value = handleResponse(response)

        }catch (e:Exception)
        {
            Timber.tag(TAG).d("exception in vm == ${e.message}")

            Timber.tag(TAG).d("cause in vm === ${e.cause}")
            _userResponse.value = NetworkResource.Error("error: ${e.message}")

            _userResponse.value = NetworkResource.Error("error c: ${e.cause}")

        }

    } // getUserProfile

    fun sharedPrefGetToken() :String? = sharedPrefsHelper.getPrefs()



    private fun handleResponse(response: Response<ProfileNetworkEntity>): NetworkResource<ProfileNetworkEntity>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.message())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    } // handle Response closed



} // ProfileViewModel closed