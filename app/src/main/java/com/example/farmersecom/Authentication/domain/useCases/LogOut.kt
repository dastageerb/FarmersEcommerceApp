package com.example.farmersecom.Authentication.domain.useCases

import com.example.farmersecom.Authentication.domain.repository.AuthRepository

class LogOut(private val repository: AuthRepository)
{
    suspend fun logOut() = repository.logOut()
}