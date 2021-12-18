package com.example.farmersecom.features.home.domain.model.sliderModels

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HomeSlider(
    @Json(name = "cloudinary_id")
    val cloudinaryId: String,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "_id")
    val id: String,
    @Json(name = "productName")
    val productName: String,
    @Json(name = "slideImage")
    val slideImage: String,
    @Json(name = "updatedAt")
    val updatedAt: String,
    @Json(name = "__v")
    val v: Int
)