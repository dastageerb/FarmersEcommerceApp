package com.example.farmersecom.features.storeAdmin.domain.model.categories


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoriesResponse(
    @Json(name = "categoryList")
    var categoryList: List<Category>?
)