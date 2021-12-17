package com.example.farmersecom.features.productStore.domain.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "category")
    val category: String,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "createdBy")
    val createdBy: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "_id")
    val id: String,
    @Json(name = "isActive")
    val isActive: Boolean,
    @Json(name = "location")
    val location: String,
    @Json(name = "price")
    val price: Int,
    @Json(name = "productName")
    val productName: String,
    @Json(name = "productPictures")
    val productPictures: List<ProductPicture>,
    @Json(name = "quantity")
    val quantity: Int,
    @Json(name = "reviews")
    val reviews: List<Any>,
    @Json(name = "slug")
    val slug: String,
    @Json(name = "storeId")
    val storeId: String,
    @Json(name = "unit")
    val unit: String,
    @Json(name = "updatedAt")
    val updatedAt: String,
    @Json(name = "__v")
    val v: Int
)