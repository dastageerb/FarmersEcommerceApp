package com.example.farmersecom.features.authentication.data.frameWork.entity.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterResponse(
    @Json(name = "status")
    val status: String,
    @Json(name = "message")
    val message: String,
)