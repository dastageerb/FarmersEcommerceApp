package com.example.farmersecom.authentication.domain.useCases

import com.example.farmersecom.authentication.data.entity.requests.RegisterEntity
import com.example.farmersecom.authentication.domain.repository.AuthRepository
import javax.inject.Inject

class Register @Inject constructor(private val repository: AuthRepository)
{

    suspend fun register(entity: RegisterEntity) = repository.registerViaEmail(entity)


}