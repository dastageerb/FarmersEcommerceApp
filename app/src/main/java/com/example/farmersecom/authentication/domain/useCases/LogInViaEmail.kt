package com.example.farmersecom.authentication.domain.useCases

import com.example.farmersecom.authentication.data.entity.requests.LogInEntity
import com.example.farmersecom.authentication.domain.repository.AuthRepository
import javax.inject.Inject


class LogInViaEmail @Inject constructor(private val repository: AuthRepository)
{
    suspend fun logInViaEmail(logInEntity: LogInEntity) = repository.logInViaEmail(logInEntity)
}
