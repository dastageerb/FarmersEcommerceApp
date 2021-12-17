package com.example.farmersecom.features.productStore.domain.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductPicture(
    @Json(name = "cloudinary_id")
    val cloudinaryId: String,
    @Json(name = "_id")
    val id: String,
    @Json(name = "img")
    val img: String
)