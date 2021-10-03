package com.example.farmersecom.features.authentication.domain.repository

import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.LogInData
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.RegisterData
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.LogInResponse
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.RegisterResponse
import retrofit2.Response

interface AuthRepository
{



   suspend fun authViaNumber()

    suspend fun registerViaEmail(registerData: RegisterData):Response<RegisterResponse>

    suspend fun logInViaEmail(logInData: LogInData): Response<LogInResponse>

    suspend fun logInViaGmail()

    suspend fun logInViaFaceBook()

    suspend fun logOut()






}