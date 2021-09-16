package com.example.farmersecom.Authentication.domain.useCases

import com.example.farmersecom.Authentication.domain.repository.AuthRepository

class LogInViaGmail(private val repository: AuthRepository)
{

  suspend fun logInViaGmail() = repository.logInViaGmail()
}