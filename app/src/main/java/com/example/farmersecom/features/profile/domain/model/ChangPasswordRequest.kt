package com.example.farmersecom.features.profile.domain.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChangPasswordRequest(
    @Json(name = "newPassword")
    var newPassword: String?,
    @Json(name = "oldPassword")
    var oldPassword: String?
)