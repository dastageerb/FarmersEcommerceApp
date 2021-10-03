package com.example.farmersecom.features.authentication.domain.useCases

import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.LogInData
import com.example.farmersecom.features.authentication.domain.repository.AuthRepository
import javax.inject.Inject


class LogInViaEmail @Inject constructor(private val repository: AuthRepository)
{
    suspend fun logInViaEmail(logInData: LogInData) = repository.logInViaEmail(logInData)
}
