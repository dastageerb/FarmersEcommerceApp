package com.example.farmersecom.features.authentication.domain.repository

import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.LogInData
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.RegisterData
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.LogInResponse
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.RegisterResponse
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthRepository
{




 suspend fun forgotPassword(email:String) : Response<StatusMsgResponse>



 suspend fun registerViaEmail(registerData: RegisterData):Response<RegisterResponse>

    suspend fun logInViaEmail(logInData: LogInData): Response<LogInResponse>

    suspend fun logOut()






}