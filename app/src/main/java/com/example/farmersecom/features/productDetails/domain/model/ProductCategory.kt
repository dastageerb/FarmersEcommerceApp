package com.example.farmersecom.features.productDetails.domain.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductCategory(
    @Json(name = "_id")
    val id: String,
    @Json(name = "name")
    val name: String
)