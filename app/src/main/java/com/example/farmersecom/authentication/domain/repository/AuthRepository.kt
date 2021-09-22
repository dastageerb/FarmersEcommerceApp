package com.example.farmersecom.authentication.domain.repository

import com.example.farmersecom.authentication.data.entity.requests.LogInEntity
import com.example.farmersecom.authentication.data.entity.requests.RegisterEntity
import com.example.farmersecom.authentication.data.entity.responses.LogInResponse
import com.example.farmersecom.authentication.data.entity.responses.RegisterResponse
import retrofit2.Response

interface AuthRepository
{



   suspend fun authViaNumber()

    suspend fun registerViaEmail(registerEntity: RegisterEntity):Response<RegisterResponse>

    suspend fun logInViaEmail(logInEntity: LogInEntity): Response<Any>

    suspend fun logInViaGmail()

    suspend fun logInViaFaceBook()

    suspend fun logOut()






}