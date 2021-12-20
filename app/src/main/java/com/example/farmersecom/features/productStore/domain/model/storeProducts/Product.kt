package com.example.farmersecom.features.productStore.domain.model.storeProducts


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "category")
    var category: String?,
    @Json(name = "description")
    var description: String?,
    @Json(name = "_id")
    var id: String?,
    @Json(name = "isActive")
    var isActive: Boolean?,
    @Json(name = "location")
    var location: String?,
    @Json(name = "price")
    var price: Int?,
    @Json(name = "productName")
    var productName: String?,
    @Json(name = "productPictures")
    var productPictures: List<ProductPicture>?,
    @Json(name = "quantity")
    var quantity: Int?,
    @Json(name = "storeId")
    var storeId: String?,
    @Json(name = "unit")
    var unit: String?
)