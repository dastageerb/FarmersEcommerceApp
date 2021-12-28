package com.example.farmersecom.features.search.domain.model.categories


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoriesResponse(
    @Json(name = "categoryList")
    var categoryList: List<Category>?
)