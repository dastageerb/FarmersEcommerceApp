package com.example.farmersecom.features.buyerSection.domain.model.orderStatus


import com.example.farmersecom.features.buyerSection.domain.model.orderStatus.Order
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderStatusResponse(
    @Json(name = "orders")
    var orders: List<Order>?
)