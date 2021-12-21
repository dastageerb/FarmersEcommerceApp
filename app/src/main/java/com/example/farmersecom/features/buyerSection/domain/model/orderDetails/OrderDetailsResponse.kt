package com.example.farmersecom.features.buyerSection.domain.model.orderDetails


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderDetailsResponse(
    @Json(name = "buyerId")
    var buyerId: String?,
    @Json(name = "city")
    var city: String?,
    @Json(name = "contactNumber")
    var contactNumber: String?,
    @Json(name = "date")
    var date: String?,
    @Json(name = "_id")
    var id: String?,
    @Json(name = "isActive")
    var isActive: Boolean?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "orderAddress")
    var orderAddress: String?,
    @Json(name = "orderNumber")
    var orderNumber: String?,
    @Json(name = "orderQuantity")
    var orderQuantity: Int?,
    @Json(name = "orderStatus")
    var orderStatus: String?,
    @Json(name = "productId")
    var productId: String?,
    @Json(name = "productImage")
    var productImage: String?,
    @Json(name = "productName")
    var productName: String?,
    @Json(name = "productprice")
    var productprice: Int?,
    @Json(name = "storeId")
    var storeId: String?,
    @Json(name = "storeImage")
    var storeImage: String?,
    @Json(name = "storeName")
    var storeName: String?,
    @Json(name = "subTotal")
    var subTotal: Int?,
    @Json(name = "totalPrice")
    var totalPrice: Int?,
    @Json(name = "unit")
    var unit: String?
)