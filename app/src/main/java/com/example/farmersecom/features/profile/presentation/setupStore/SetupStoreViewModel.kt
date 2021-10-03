package com.example.farmersecom.features.profile.presentation.setupStore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akhbar.utils.NetworkResource
import com.example.farmersecom.features.profile.data.framework.entities.SetUpStoreResponse
import com.example.farmersecom.features.profile.data.framework.entities.SetupStoreData
import com.example.farmersecom.features.profile.domain.usecase.SetupStoreUseCase
import com.example.farmersecom.utils.ErrorBodyExtension.getMessage
import com.example.farmersecom.utils.HandleErrorExtension.handleException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SetupStoreViewModel @Inject constructor(private val setupStoreUseCase: SetupStoreUseCase):ViewModel()
{

    private val _setupStoreResponse: MutableStateFlow<NetworkResource<SetUpStoreResponse>>
            = MutableStateFlow(NetworkResource.None())
    val setupStoreResponse: StateFlow<NetworkResource<SetUpStoreResponse>> = _setupStoreResponse




    fun setupStore(setupStoreData: SetupStoreData) = viewModelScope.launch(Dispatchers.IO)
    {
        _setupStoreResponse.value = NetworkResource.Loading()
        try
        {
            val response = setupStoreUseCase.setupStore(setupStoreData)

            _setupStoreResponse.value = handleSetupStoreResponse(response)
        }catch (e:Exception)
        {
            _setupStoreResponse.value = NetworkResource.Error(e.handleException())
        } //
    } // getProfile closed

    private fun handleSetupStoreResponse(response: Response<SetUpStoreResponse>):
            NetworkResource<SetUpStoreResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed

    } // handleSetupStoreResponse


} // SetupStoreViewModel