package com.example.farmersecom.Authentication.data.entity.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterResponse(
    @Json(name = "status")
    val status: String,
    @Json(name = "message")
    val message: String,
)