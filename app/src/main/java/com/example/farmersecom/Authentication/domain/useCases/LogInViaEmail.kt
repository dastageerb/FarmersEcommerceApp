package com.example.farmersecom.Authentication.domain.useCases

import com.example.farmersecom.Authentication.data.entity.requests.LogInEntity
import com.example.farmersecom.Authentication.domain.repository.AuthRepository

class LogInViaEmail(private val repository: AuthRepository)
{
    suspend fun logInViaEmail(logInEntity: LogInEntity) = repository.logInViaEmail(logInEntity)
}