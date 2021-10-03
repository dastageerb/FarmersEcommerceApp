package com.example.farmersecom.features.authentication.data.business

import com.example.farmersecom.features.authentication.data.frameWork.AuthApi
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.LogInData
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.RegisterData
import com.example.farmersecom.features.authentication.domain.repository.AuthRepository

class AuthRepositoryImpl(private val authApi: AuthApi) : AuthRepository
{
    override suspend fun authViaNumber()
    {
        TODO("Not yet implemented")
    }

    override suspend fun registerViaEmail(registerData: RegisterData)
    = authApi.registerViaEmail(registerData)


    override suspend fun logInViaEmail(logInData: LogInData)
    = authApi.logInViaEmail(logInData)

    override suspend fun logInViaGmail()
    {
        TODO("Not yet implemented")
    }

    override suspend fun logInViaFaceBook()
    {
        TODO("Not yet implemented")
    }

    override suspend fun logOut()
    {
        TODO("Not yet implemented")
    }






} // AuthRepository