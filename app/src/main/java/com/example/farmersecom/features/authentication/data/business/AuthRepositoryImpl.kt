package com.example.farmersecom.features.authentication.data.business

import com.example.farmersecom.features.authentication.data.frameWork.AuthApi
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.ForgotPasswordData
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.LogInData
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.RegisterData
import com.example.farmersecom.features.authentication.domain.repository.AuthRepository
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import retrofit2.Response

class AuthRepositoryImpl(private val authApi: AuthApi) : AuthRepository
{

    override suspend fun forgotPassword(email: String): Response<StatusMsgResponse>
    = authApi.forgotPassword(ForgotPasswordData(email))



    override suspend fun registerViaEmail(registerData: RegisterData)
    = authApi.registerViaEmail(registerData)


    override suspend fun logInViaEmail(logInData: LogInData)
    = authApi.logInViaEmail(logInData)


    override suspend fun logOut()
    {
        TODO("Not yet implemented")
    }


} // AuthRepository