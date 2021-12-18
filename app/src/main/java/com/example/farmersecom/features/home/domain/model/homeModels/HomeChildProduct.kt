package com.example.farmersecom.features.home.domain.model.homeModels

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HomeChildProduct(
    @Json(name = "productId")
    val productId: String,
    @Json(name = "productName")
    val productName: String,
    @Json(name = "thumbnail")
    val thumbnail: String
)
