package com.example.farmersecom.Authentication.domain.useCases

import com.example.farmersecom.Authentication.domain.repository.AuthRepository

class AuthViaNumber(private val repository: AuthRepository)
{
    suspend fun authViaNumber() = repository.authViaNumber()
}