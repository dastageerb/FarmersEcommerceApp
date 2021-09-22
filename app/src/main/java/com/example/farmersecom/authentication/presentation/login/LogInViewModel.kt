package com.example.farmersecom.authentication.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akhbar.utils.NetworkResource
import com.example.farmersecom.authentication.data.entity.requests.LogInEntity
import com.example.farmersecom.authentication.data.entity.requests.RegisterEntity
import com.example.farmersecom.authentication.data.entity.responses.LogInResponse
import com.example.farmersecom.authentication.data.entity.responses.RegisterResponse
import com.example.farmersecom.authentication.domain.useCases.LogInViaEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.login.LoginException

@HiltViewModel
class LogInViewModel @Inject constructor(private val login:LogInViaEmail) : ViewModel()
{


    private val _loginResponse: MutableStateFlow<NetworkResource<Any>>
            = MutableStateFlow(NetworkResource.None())

    val loginResponse get() = _loginResponse

    fun loginUser(logInEntity: LogInEntity) = viewModelScope.launch(Dispatchers.IO)
    {
        _loginResponse.value = NetworkResource.Loading()
        try
        {
            val response = login.logInViaEmail(logInEntity)
            _loginResponse.value = handleResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _loginResponse.value = NetworkResource.Error("Http Exception")
                else -> _loginResponse.value = NetworkResource.Error("No Internet Connection")
            } // when closed
        }
    }

    private fun handleResponse(response: Response<Any>): NetworkResource<Any>
    {
        return when(response.code())
        {
            200 -> NetworkResource.Success(response.body())
            400  -> NetworkResource.Error(response.raw().body.toString())
            else -> NetworkResource.Error("Something went wrong ${response.code()}")
        } // when closed
    } // handle Response closed







} // logInViewModel closed

