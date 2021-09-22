package com.example.farmersecom.authentication.data.entity.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogInResponse(
    @Json(name = "token")
    val token: String?,
    @Json(name = "user")
    val user: User?
)