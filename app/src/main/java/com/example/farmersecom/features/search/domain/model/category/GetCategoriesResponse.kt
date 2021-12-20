package com.example.farmersecom.features.search.domain.model.category


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCategoriesResponse(
    @Json(name = "categoryList")
    var categoryList: List<Category>?
)