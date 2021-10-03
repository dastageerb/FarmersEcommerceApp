package com.example.farmersecom.features.authentication.data.frameWork.entity.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogInData(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String
)