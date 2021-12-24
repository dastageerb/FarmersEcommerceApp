package com.example.farmersecom.features.storeAdmin.domain.model.updateStore


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateStore(
    @Json(name = "aboutStore")
    var aboutStore: String?,
    @Json(name = "storeName")
    var storeName: String?
)