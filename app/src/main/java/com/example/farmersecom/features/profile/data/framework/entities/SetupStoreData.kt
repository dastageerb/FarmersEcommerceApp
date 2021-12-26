package com.example.farmersecom.features.profile.data.framework.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SetupStoreData(
    @Json(name = "aboutStore")
    val description: String,
    @Json(name = "storeName")
    val shopName: String,
    @Json(name = "deliveryOutCity")
    val deliversOutOfCity:Boolean,
)