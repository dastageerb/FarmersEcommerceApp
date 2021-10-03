package com.example.farmersecom.features.authentication.data.frameWork

import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.LogInData
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.RegisterData
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.LogInResponse
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi
{


    @POST("api/register")
    suspend fun registerViaEmail(@Body registerData: RegisterData) : Response<RegisterResponse>


    @POST("api/login")
    suspend fun logInViaEmail(@Body logInData: LogInData) : Response<LogInResponse>



}