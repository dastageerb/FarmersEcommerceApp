package com.example.farmersecom.authentication.domain.useCases

import com.example.farmersecom.authentication.domain.repository.AuthRepository

class AuthViaNumber(private val repository: AuthRepository)
{
    suspend fun authViaNumber() = repository.authViaNumber()
}