package com.example.farmersecom.authentication.domain.useCases

import com.example.farmersecom.authentication.domain.repository.AuthRepository

class LogInViaFacebook(private val repository: AuthRepository)
{
    suspend fun logInViaFacebook() = repository.logInViaFaceBook()
}