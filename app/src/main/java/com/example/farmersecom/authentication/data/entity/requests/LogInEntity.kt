package com.example.farmersecom.authentication.data.entity.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogInEntity(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String
)