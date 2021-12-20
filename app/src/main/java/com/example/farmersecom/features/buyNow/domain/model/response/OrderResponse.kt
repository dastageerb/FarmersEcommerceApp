package com.example.farmersecom.features.buyNow.domain.model.response


import com.example.farmersecom.features.buyNow.domain.model.response.Order
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderResponse(
    @Json(name = "message")
    var message: String?,
    @Json(name = "order")
    var order: Order?,
    @Json(name = "status")
    var status: String?
)