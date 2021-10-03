package com.example.farmersecom.features.profile.data.framework.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SetupStoreData(
    @Json(name = "description")
    val description: String,
    @Json(name = "shopName")
    val shopName: String
)