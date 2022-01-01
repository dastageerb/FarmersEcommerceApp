package com.example.farmersecom.features.communitySection.domain.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post(
    @Json(name = "cloudinary_id")
    var cloudinaryId: String?,
    @Json(name = "createdAt")
    var createdAt: String?,
    @Json(name = "createdBy")
    var createdBy: String?,
    @Json(name = "description")
    var description: String?,
    @Json(name = "_id")
    var id: String?,
    @Json(name = "image")
    var image: String?,
    @Json(name = "title")
    var title: String?,
    @Json(name = "updatedAt")
    var updatedAt: String?,
    @Json(name = "__v")
    var v: Int?
)