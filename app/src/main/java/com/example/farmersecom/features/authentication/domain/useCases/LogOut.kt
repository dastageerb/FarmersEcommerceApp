package com.example.farmersecom.features.authentication.domain.useCases

import com.example.farmersecom.features.authentication.domain.repository.AuthRepository

class LogOut(private val repository: AuthRepository)
{
    suspend fun logOut() = repository.logOut()
}