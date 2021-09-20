package com.example.farmersecom.authentication.data

import com.example.akhbar.utils.NetworkResource
import com.example.farmersecom.authentication.data.entity.requests.LogInEntity
import com.example.farmersecom.authentication.data.entity.requests.RegisterEntity
import com.example.farmersecom.authentication.data.entity.responses.LogInResponse
import com.example.farmersecom.authentication.data.entity.responses.RegisterResponse
import com.example.farmersecom.authentication.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class AuthRepositoryImpl(private val authApi: AuthApi) : AuthRepository
{
    override suspend fun authViaNumber()
    {
        TODO("Not yet implemented")
    }

    override suspend fun registerViaEmail(registerEntity: RegisterEntity)
    = authApi.registerViaEmail(registerEntity)


    override suspend fun logInViaEmail(logInEntity: LogInEntity): Response<LogInResponse>
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