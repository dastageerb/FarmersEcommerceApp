package com.example.farmersecom.features.orderDetails.domain

import com.google.gson.JsonObject
import com.squareup.moshi.Json
import retrofit2.Response

interface PlaceOrderRepository
{

    suspend fun placeOrder():Response<JsonObject>


}