package com.example.farmersecom.Authentication.domain.useCases

import com.example.farmersecom.Authentication.domain.repository.AuthRepository

class LogInViaFacebook(private val repository: AuthRepository)
{
    suspend fun logInViaFacebook() = repository.logInViaFaceBook()
}