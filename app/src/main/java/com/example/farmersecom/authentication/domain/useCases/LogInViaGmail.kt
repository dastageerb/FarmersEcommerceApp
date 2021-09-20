package com.example.farmersecom.authentication.domain.useCases

import com.example.farmersecom.authentication.domain.repository.AuthRepository

class LogInViaGmail(private val repository: AuthRepository)
{

  suspend fun logInViaGmail() = repository.logInViaGmail()
}