package com.example.farmersecom.features.productDetails.domain.model
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Store(
    @Json(name = "_id")
    var id: String?,
    @Json(name = "storeImage")
    var storeImage: String?,
    @Json(name = "storeName")
    var storeName: String?
)