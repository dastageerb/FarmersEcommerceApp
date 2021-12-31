package com.example.farmersecom.features.storeAdmin.domain.model.editProduct


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EditProduct(
    @Json(name = "category")
    var category: String?,
    @Json(name = "description")
    var description: String?,
    @Json(name = "productName")
    var name: String?,
    @Json(name = "price")
    var price: Int?,
    @Json(name = "unit")
    var unit: String?,
    @Json(name = "location")
    var location: String?
)