package com.example.farmersecom.features.authentication.data.frameWork

import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.ForgotPasswordData
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.LogInData
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.RegisterData
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.LogInResponse
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.RegisterResponse
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi
{


    @POST("api/register")
    suspend fun registerViaEmail(@Body registerData: RegisterData) : Response<RegisterResponse>



    @POST("api/forget-password")
    suspend fun forgotPassword(@Body forgotPasswordData: ForgotPasswordData) : Response<StatusMsgResponse>


    @POST("api/login")
    suspend fun logInViaEmail(@Body logInData: LogInData) : Response<LogInResponse>



}