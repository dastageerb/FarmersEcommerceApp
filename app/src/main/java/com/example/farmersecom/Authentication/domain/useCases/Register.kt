package com.example.farmersecom.Authentication.domain.useCases

import com.example.farmersecom.Authentication.data.entity.requests.RegisterEntity
import com.example.farmersecom.Authentication.domain.repository.AuthRepository

class Register(private val repository: AuthRepository)
{

    suspend fun register(entity:RegisterEntity) = repository.registerViaEmail(entity)


}