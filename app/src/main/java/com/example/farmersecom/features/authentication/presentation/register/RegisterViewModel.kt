package com.example.farmersecom.features.authentication.presentation.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.R
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.RegisterData
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.RegisterResponse
import com.example.farmersecom.features.authentication.domain.useCases.Register
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private  val register: Register) : ViewModel()
{

    private val _registerResponse:MutableSharedFlow<NetworkResource<RegisterResponse>>
        = MutableSharedFlow(0)
    val registerResponse: SharedFlow<NetworkResource<RegisterResponse>>
            = _registerResponse


    fun register(registerData: RegisterData) = viewModelScope.launch(Dispatchers.IO)
    {
        Timber.tag(TAG).d(registerData.toString())
        _registerResponse.emit(NetworkResource.Loading())
          try
            {
                val response = register.register(registerData)
                _registerResponse.emit( handleResponse(response))
            }catch (e:Exception)
            {
                when (e)
                {
                    is HttpException -> _registerResponse.emit( NetworkResource.Error(e.message))
                    else -> _registerResponse.emit( NetworkResource.Error(e.message))
                } // when closed
            } // catch closed
    } // register closed

    private fun handleResponse(response: Response<RegisterResponse>): NetworkResource<RegisterResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Error : "+response.code())
        } // when closed
    } // handle Response closed


} // RegisterViewModel closed