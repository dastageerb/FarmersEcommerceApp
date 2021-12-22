package com.example.farmersecom.features.storeAdmin.domain.model.productStatusResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductStatus(
    @Json(name = "category")
    var category: Category?,
    @Json(name = "description")
    var description: String?,
    @Json(name = "_id")
    var id: String?,
    @Json(name = "isActive")
    var isActive: Boolean?,
    @Json(name = "price")
    var price: Int?,
    @Json(name = "productName")
    var productName: String?,
    @Json(name = "productPictures")
    var productPictures: List<ProductPicture>?,
    @Json(name = "quantity")
    var quantity: Int?,
    @Json(name = "unit")
    var unit: String?)
