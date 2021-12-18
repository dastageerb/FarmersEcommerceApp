package com.example.farmersecom.features.productDetails.domain.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDetailsResponse(
    @Json(name = "productCategory")
    val productCategory: ProductCategory,
    @Json(name = "productDescription")
    val productDescription: String,
    @Json(name = "productId")
    val productId: String,
    @Json(name = "productLocation")
    val productLocation: String,
    @Json(name = "productName")
    val productName: String,
    @Json(name = "productPictures")
    val productPictures: List<ProductPicture>,
    @Json(name = "productPrice")
    val productPrice: Int,
    @Json(name = "productQuantity")
    val productQuantity: Int,
    @Json(name = "productUnit")
    val productUnit: String,
    @Json(name = "store")
    var store: Store?
)