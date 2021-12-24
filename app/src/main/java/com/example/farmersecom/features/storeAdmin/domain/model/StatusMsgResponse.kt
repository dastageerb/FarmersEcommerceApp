package com.example.farmersecom.features.storeAdmin.domain.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StatusMsgResponse(
    @Json(name = "message")
    var message: String?,
    @Json(name = "status")
    var status: String?
)