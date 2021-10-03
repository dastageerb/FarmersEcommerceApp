package com.example.farmersecom.features.authentication.data.frameWork.entity.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "email")
    val email: String?,
    @Json(name = "fullName")
    val fullName: String?,
    @Json(name = "_id")
    val id: String?
)