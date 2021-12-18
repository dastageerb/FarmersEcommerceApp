package com.example.farmersecom.features.productStore.domain.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StoreDetailsResponse(
    @Json(name = "about")
    var about: String?,
    @Json(name = "createdAt")
    var createdAt: String?,
    @Json(name = "_id")
    var id: String?,
    @Json(name = "owner")
    var owner: String?,
    @Json(name = "storeImage")
    var storeImage: String?,
    @Json(name = "storeName")
    var storeName: String?,
    @Json(name = "updatedAt")
    var updatedAt: String?,
    @Json(name = "__v")
    var v: Int?
)