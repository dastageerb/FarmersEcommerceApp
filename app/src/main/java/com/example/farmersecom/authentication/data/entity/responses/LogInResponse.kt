package com.example.farmersecom.authentication.data.entity.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogInResponse(
    @Json(name = "status")
    val status: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "token")
    val token:String

)