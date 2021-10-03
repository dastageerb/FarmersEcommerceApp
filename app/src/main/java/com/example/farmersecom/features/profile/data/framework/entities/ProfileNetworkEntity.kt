package com.example.farmersecom.features.profile.data.framework.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileNetworkEntity(
    @Json(name = "fullName")
    val fullName: String,
    @Json(name = "isSeller")
    val isSeller: Boolean,
    @Json(name = "userImgUrl")
    val userImgUrl: String
)