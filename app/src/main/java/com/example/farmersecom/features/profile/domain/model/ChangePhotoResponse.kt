package com.example.farmersecom.features.profile.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChangePhotoResponse(
    @Json(name = "status")
    val status: String,
    @Json(name = "profilePicture")
    val userImgUrl: String
)