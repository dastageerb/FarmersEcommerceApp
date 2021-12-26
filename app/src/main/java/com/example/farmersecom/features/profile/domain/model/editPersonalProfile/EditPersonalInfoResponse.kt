package com.example.farmersecom.features.profile.domain.model.editPersonalProfile


import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EditPersonalInfoResponse(
    @Json(name = "message")
    var message: String?,
    @Json(name = "status")
    var status: String?,
    @Json(name = "user")
    var user:User
)