package com.example.farmersecom.Authentication.data

import com.example.akhbar.utils.NetworkResource
import com.example.farmersecom.Authentication.data.entity.requests.LogInEntity
import com.example.farmersecom.Authentication.data.entity.requests.RegisterEntity
import com.example.farmersecom.Authentication.data.entity.responses.LogInResponse
import com.example.farmersecom.Authentication.data.entity.responses.RegisterResponse
import com.example.farmersecom.Authentication.domain.repository.AuthRepository

class AuthRepositoryImpl(val authApi: AuthApi) : AuthRepository
{
    override suspend fun authViaNumber()
    {
        TODO("Not yet implemented")
    }

    override suspend fun registerViaEmail(registerEntity: RegisterEntity): NetworkResource<RegisterResponse>
    {
        TODO("Not yet implemented")
    }

    override suspend fun logInViaEmail(logInEntity: LogInEntity): NetworkResource<LogInResponse>
    {
        TODO("Not yet implemented")
    }

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