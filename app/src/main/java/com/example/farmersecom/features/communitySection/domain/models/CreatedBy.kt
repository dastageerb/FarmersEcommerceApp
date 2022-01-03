package com.example.farmersecom.features.communitySection.domain.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreatedBy(
    @Json(name = "firstName")
    var firstName: String?,
    @Json(name = "_id")
    var id: String?,
    @Json(name = "lastName")
    var lastName: String?
)