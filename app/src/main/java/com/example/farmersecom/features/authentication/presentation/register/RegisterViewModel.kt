package com.example.farmersecom.features.authentication.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akhbar.utils.NetworkResource
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.RegisterData
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.RegisterResponse
import com.example.farmersecom.features.authentication.domain.useCases.Register
import com.example.farmersecom.utils.ErrorBodyExtension.getMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private  val register: Register) : ViewModel()
{

    private val _registerResponse:MutableStateFlow<NetworkResource<RegisterResponse>>
        = MutableStateFlow(NetworkResource.None())
    val registerResponse: StateFlow<NetworkResource<RegisterResponse>>
            = _registerResponse


    fun register(registerData: RegisterData) = viewModelScope.launch(Dispatchers.IO)
    {
        _registerResponse.value = NetworkResource.Loading()
          try
            {
                val response = register.register(registerData)
                _registerResponse.value = handleResponse(response)
            }catch (e:Exception)
            {
                when (e)
                {
                    is HttpException -> _registerResponse.value =NetworkResource.Error("Something went wrong")
                    else -> _registerResponse.value = NetworkResource.Error("No Internet Connection")
                } // when closed
            }
    }

    private fun handleResponse(response: Response<RegisterResponse>): NetworkResource<RegisterResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    } // handle Response closed


} // RegisterViewModel closed