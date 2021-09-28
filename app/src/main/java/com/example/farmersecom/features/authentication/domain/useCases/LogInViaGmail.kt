package com.example.farmersecom.features.authentication.domain.useCases

import com.example.farmersecom.features.authentication.domain.repository.AuthRepository

class LogInViaGmail(private val repository: AuthRepository)
{

  suspend fun logInViaGmail() = repository.logInViaGmail()
}