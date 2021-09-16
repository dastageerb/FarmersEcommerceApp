package com.example.farmersecom.Authentication.domain.repository

import com.example.akhbar.utils.NetworkResource
import com.example.farmersecom.Authentication.data.entity.requests.LogInEntity
import com.example.farmersecom.Authentication.data.entity.requests.RegisterEntity
import com.example.farmersecom.Authentication.data.entity.responses.LogInResponse
import com.example.farmersecom.Authentication.data.entity.responses.RegisterResponse

interface AuthRepository
{



   suspend fun authViaNumber()

    suspend fun registerViaEmail(registerEntity: RegisterEntity): NetworkResource<RegisterResponse>

    suspend fun logInViaEmail(logInEntity: LogInEntity): NetworkResource<LogInResponse>

    suspend fun logInViaGmail()

    suspend fun logInViaFaceBook()

    suspend fun logOut()






}