package com.example.farmersecom.features.productStore.domain.model.storeProducts


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductPicture(
    @Json(name = "cloudinary_id")
    var cloudinaryId: String?,
    @Json(name = "_id")
    var id: String?,
    @Json(name = "img")
    var img: String?
)