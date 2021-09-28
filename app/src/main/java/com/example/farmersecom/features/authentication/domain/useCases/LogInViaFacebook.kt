package com.example.farmersecom.features.authentication.domain.useCases

import com.example.farmersecom.features.authentication.domain.repository.AuthRepository

class LogInViaFacebook(private val repository: AuthRepository)
{
    suspend fun logInViaFacebook() = repository.logInViaFaceBook()
}