package com.example.farmersecom.features.authentication.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akhbar.utils.NetworkResource
import com.example.farmersecom.SharedPrefsHelper
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.LogInData
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.LogInResponse
import com.example.farmersecom.features.authentication.domain.useCases.LogInViaEmail
import com.example.farmersecom.utils.ErrorBodyExtension.getMessage
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Error
import javax.inject.Inject
import org.json.JSONObject




@HiltViewModel
class LogInViewModel @Inject constructor(private val login:LogInViaEmail,
                                         private val sharedPrefsHelper: SharedPrefsHelper) : ViewModel()
{


    private val _loginResponse: MutableStateFlow<NetworkResource<LogInResponse>>
            = MutableStateFlow(NetworkResource.None())

    val loginResponse:StateFlow<NetworkResource<LogInResponse>> = _loginResponse

    fun loginUser(logInData: LogInData) = viewModelScope.launch(Dispatchers.IO)
    {
        _loginResponse.value = NetworkResource.Loading()
        try
        {
            val response = login.logInViaEmail(logInData)
            _loginResponse.value = handleResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _loginResponse.value = NetworkResource.Error("Http Exception")
                else -> _loginResponse.value = NetworkResource.Error("No Internet Connection: ${e.message}")
            } // when closed
        }
    }

    private fun handleResponse(response: Response<LogInResponse>): NetworkResource<LogInResponse>
    {
        return when(response.code())
        {
            200 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went wrong ${response.code()}")
        } // when closed
    } // handle Response closed






    fun saveAuthToken(token: String) = sharedPrefsHelper.saveToken(token)
    fun getAuthToken() :String? = sharedPrefsHelper.getToken()



} // logInViewModel closed

