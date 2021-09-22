package com.example.farmersecom.authentication.data.entity.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "email")
    val email: String?,
    @Json(name = "firstName")
    val firstName: String?,
    @Json(name = "fullName")
    val fullName: String?,
    @Json(name = "_id")
    val id: String?,
    @Json(name = "lastName")
    val lastName: String?
)