package com.example.farmersecom.features.home.domain.model.more


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoreResponseItem(
    @Json(name = "products")
    var products: List<MoreProduct>?
)