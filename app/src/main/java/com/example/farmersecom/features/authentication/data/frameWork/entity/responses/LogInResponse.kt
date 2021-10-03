package com.example.farmersecom.features.authentication.data.frameWork.entity.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.Response

@JsonClass(generateAdapter = true)
data class LogInResponse(
    @Json(name = "message")
    val message: String?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "token")
    val token: String?,
    @Json(name = "user")
    val user: User?
)