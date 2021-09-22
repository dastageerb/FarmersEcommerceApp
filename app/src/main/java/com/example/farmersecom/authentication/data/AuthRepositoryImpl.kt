package com.example.farmersecom.authentication.data

import com.example.farmersecom.authentication.data.entity.requests.LogInEntity
import com.example.farmersecom.authentication.data.entity.requests.RegisterEntity
import com.example.farmersecom.authentication.data.entity.responses.LogInResponse
import com.example.farmersecom.authentication.domain.repository.AuthRepository
import retrofit2.Response

class AuthRepositoryImpl(private val authApi: AuthApi) : AuthRepository
{
    override suspend fun authViaNumber()
    {
        TODO("Not yet implemented")
    }

    override suspend fun registerViaEmail(registerEntity: RegisterEntity)
    = authApi.registerViaEmail(registerEntity)


    override suspend fun logInViaEmail(logInEntity: LogInEntity)
    = authApi.logInViaEmail(logInEntity)

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