package com.example.farmersecom.features.home.domain.model.homeModels

import com.example.farmersecom.features.home.domain.model.homeModels.HomeChildProduct
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HomeLatestItem(
    @Json(name = "categoryId")
    val categoryId: String,
    @Json(name = "categoryName")
    val categoryName: String,
    @Json(name = "products")
    val products: List<HomeChildProduct>)