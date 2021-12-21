package com.example.farmersecom.features.storeAdmin.domain.model.categories


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(
    @Json(name = "children")
    var children: List<Any>?,
    @Json(name = "_id")
    var id: String?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "slug")
    var slug: String?
)