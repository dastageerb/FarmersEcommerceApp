package com.example.farmersecom.features.productStore.domain.model.storeDetails


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Owner(
    @Json(name = "firstName")
    var firstName: String?,
    @Json(name = "_id")
    var id: String?,
    @Json(name = "lastName")
    var lastName: String?,
    @Json(name = "profilePicture")
    var profilePicture: String?
)