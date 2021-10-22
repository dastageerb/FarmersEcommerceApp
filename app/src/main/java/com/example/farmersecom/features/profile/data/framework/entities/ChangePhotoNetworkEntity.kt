package com.example.farmersecom.features.profile.data.framework.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChangePhotoNetworkEntity(
    @Json(name = "message")
    val message: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "userImgUrl")
    val userImgUrl: String
)