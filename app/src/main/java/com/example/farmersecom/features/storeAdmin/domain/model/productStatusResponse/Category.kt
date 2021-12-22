package com.example.farmersecom.features.storeAdmin.domain.model.productStatusResponse


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(
    @Json(name = "_id")
    var id: String?,
    @Json(name = "name")
    var name: String?
)