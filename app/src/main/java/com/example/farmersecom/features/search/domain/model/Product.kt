package com.example.farmersecom.features.search.domain.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "productCategory")
    var productCategory: String?,
    @Json(name = "productId")
    var productId: String?,
    @Json(name = "productLocation")
    var productLocation: String?,
    @Json(name = "productName")
    var productName: String?,
    @Json(name = "productPicture")
    var productPicture: String?,
    @Json(name = "productPrice")
    var productPrice: Int?,
    @Json(name = "productQuantity")
    var productQuantity: Int?,
    @Json(name = "productUnit")
    var productUnit: String?
)