package com.example.farmersecom.authentication.data

import com.example.farmersecom.authentication.data.entity.requests.LogInEntity
import com.example.farmersecom.authentication.data.entity.requests.RegisterEntity
import com.example.farmersecom.authentication.data.entity.responses.LogInResponse
import com.example.farmersecom.authentication.data.entity.responses.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi
{


    @POST("api/signup")
    suspend fun registerViaEmail(@Body registerEntity: RegisterEntity) : Response<RegisterResponse>


    @POST("api/signin")
    suspend fun logInViaEmail(@Body logInEntity: LogInEntity) : Response<LogInResponse>



}