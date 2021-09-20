package com.example.farmersecom.authentication.domain.useCases

import com.example.farmersecom.authentication.domain.repository.AuthRepository

class LogOut(private val repository: AuthRepository)
{
    suspend fun logOut() = repository.logOut()
}