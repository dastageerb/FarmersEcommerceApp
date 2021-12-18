package com.example.farmersecom.features.productDetails.domain.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDetailsResponse(
    @Json(name = "productCategory")
    var productCategory: ProductCategory?,
    @Json(name = "productDeliveryCharges")
    var productDeliveryCharges: Int?,
    @Json(name = "productDescription")
    var productDescription: String?,
    @Json(name = "productId")
    var productId: String?,
    @Json(name = "productLocation")
    var productLocation: String?,
    @Json(name = "productName")
    var productName: String?,
    @Json(name = "productPictures")
    var productPictures: List<ProductPicture>?,
    @Json(name = "productPrice")
    var productPrice: Int?,
    @Json(name = "productQuantity")
    var productQuantity: Int?,
    @Json(name = "productRating")
    var productRating: Double?,
    @Json(name = "productUnit")
    var productUnit: String?,
    @Json(name = "store")
    var store: Store?
)