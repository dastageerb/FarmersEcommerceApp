package com.example.farmersecom.features.productStore.domain.model.storeDetails


import com.example.farmersecom.features.productStore.domain.model.storeDetails.Owner
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StoreDetailsResponse(
    @Json(name = "aboutStore")
    var about: String?,
    @Json(name = "createdAt")
    var createdAt: String?,
    @Json(name = "_id")
    var id: String?,
    @Json(name = "owner")
    var owner: Owner,
    @Json(name = "storeImage")
    var storeImage: String?,
    @Json(name = "storeName")
    var storeName: String?,
    @Json(name = "updatedAt")
    var updatedAt: String?,
    @Json(name = "__v")
    var v: Int?,
    @Json(name = "deliveryOutCity")
    var deliversOfOutCity:Boolean
)