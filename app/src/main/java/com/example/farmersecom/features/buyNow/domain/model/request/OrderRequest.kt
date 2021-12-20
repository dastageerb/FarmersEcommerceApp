package com.example.farmersecom.features.buyNow.domain.model.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderRequest(
    @Json(name = "address")
    var address: String?,
    @Json(name = "city")
    var city: String?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "paymentOption")
    var paymentOption: String?,
    @Json(name = "postalCode")
    var postalCode: Int?,
    @Json(name = "quantity")
    var quantity: Int?,
    @Json(name="contactNumber")
    var contactNumber:String
)