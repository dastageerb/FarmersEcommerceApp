package com.example.farmersecom.features.authentication.domain.useCases

import com.example.farmersecom.features.authentication.domain.repository.AuthRepository

class AuthViaNumber(private val repository: AuthRepository)
{
    suspend fun authViaNumber() = repository.authViaNumber()
}