package com.example.farmersecom.features.authentication.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.SharedPrefsHelper
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.LogInData
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.LogInResponse
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.User
import com.example.farmersecom.features.authentication.domain.useCases.ForgotPasswordUseCase
import com.example.farmersecom.features.authentication.domain.useCases.LogInViaEmail
import com.example.farmersecom.features.home.domain.usecases.UpdateFCMToken
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class LogInViewModel @Inject constructor(private val login:LogInViaEmail,
                                        private val forgotPasswordUseCase: ForgotPasswordUseCase,
                                         private val sharedPrefsHelper: SharedPrefsHelper
                                         ,private val updateFCMToken: UpdateFCMToken) : ViewModel()
{


    private val _loginResponse: MutableSharedFlow<NetworkResource<LogInResponse>>
            = MutableSharedFlow(0)

    val loginResponse:SharedFlow<NetworkResource<LogInResponse>> = _loginResponse

    fun loginUser(logInData: LogInData) = viewModelScope.launch(Dispatchers.IO)
    {
        _loginResponse.emit(NetworkResource.Loading())
        try
        {
            val response = login.logInViaEmail(logInData)
            _loginResponse.emit(handleResponse(response))
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _loginResponse.emit(NetworkResource.Error(e.message))
                else ->  _loginResponse.emit(NetworkResource.Error(e.message))
            } // when closed
        } // catch closed
    } // loginUser closed

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
    fun clearToken()  = sharedPrefsHelper.clearToken()


    fun saveUser(user:User ) = sharedPrefsHelper.saveUser(user)

    // forgotPassword UseCase


    private var _statusMsgResponse: MutableSharedFlow<NetworkResource<StatusMsgResponse>>
            = MutableSharedFlow(replay = 0)
    val statusMsgResponse: SharedFlow<NetworkResource<StatusMsgResponse>>
            = _statusMsgResponse



    fun forgotPassword(email:String) = viewModelScope.launch(Dispatchers.IO)
    {

        _statusMsgResponse.emit(NetworkResource.Loading())
        try
        {
            val response = forgotPasswordUseCase.forgotPasswordUseCase(email)
            _statusMsgResponse.emit(handleStatusMsgResponse(response))
            //     _statusMsgResponse.value = handleStatusMessageResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException ->{  _statusMsgResponse.emit(NetworkResource.Error(e.message)) }
                else ->
                {
                    _statusMsgResponse.emit(NetworkResource.Error(e.message))
                }
            } // when closed
        }

    } //  changeProductStatus closed


    fun updateFCMToken() = viewModelScope.launch(Dispatchers.IO)
    {
        _statusMsgResponse.emit(NetworkResource.Loading())
        try
        {
            val token = FirebaseMessaging.getInstance().token.await()
            val response = updateFCMToken.updateFCMToken(token)
            _statusMsgResponse.emit(handleStatusMsgResponse(response))

        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException ->{  _statusMsgResponse.emit(NetworkResource.Error(e.message)) }
                else ->
                {
                    _statusMsgResponse.emit(NetworkResource.Error(e.message))
                }
            } // when closed
        }

    } // updateFCM


    private fun handleStatusMsgResponse(response: Response<StatusMsgResponse>): NetworkResource<StatusMsgResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400,404 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    }



} // logInViewModel closed

