package com.example.farmersecom.features.authentication.domain.useCases

import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.RegisterData
import com.example.farmersecom.features.authentication.domain.repository.AuthRepository
import javax.inject.Inject

class Register @Inject constructor(private val repository: AuthRepository)
{

    suspend fun register(data: RegisterData) = repository.registerViaEmail(data)


}