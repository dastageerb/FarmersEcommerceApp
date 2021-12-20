package com.example.farmersecom.features.buyerSection.domain.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Order(
    @Json(name = "buyerId")
    var buyerId: String?,
    @Json(name = "city")
    var city: String?,
    @Json(name = "contactNumber")
    var contactNumber: String?,
    @Json(name = "createdAt")
    var createdAt: String?,
    @Json(name = "deliveryCharges")
    var deliveryCharges: Int?,
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
    @Json(name = "paymentOption")
    var paymentOption: String?,
    @Json(name = "postalCode")
    var postalCode: Int?,
    @Json(name = "productId")
    var productId: String?,
    @Json(name = "storeId")
    var storeId: String?,
    @Json(name = "subTotal")
    var subTotal: Int?,
    @Json(name = "totalPrice")
    var totalPrice: Int?,
    @Json(name = "updatedAt")
    var updatedAt: String?,
    @Json(name = "__v")
    var v: Int?
)