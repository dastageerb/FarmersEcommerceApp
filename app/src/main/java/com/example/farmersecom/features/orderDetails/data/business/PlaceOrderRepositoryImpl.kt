package com.example.farmersecom.features.orderDetails.data.business

import com.example.farmersecom.features.orderDetails.data.framwork.OrderApi
import com.example.farmersecom.features.orderDetails.domain.PlaceOrderRepository
import com.google.gson.JsonObject
import retrofit2.Response

class PlaceOrderRepositoryImpl(private val orderApi: OrderApi) : PlaceOrderRepository
{
    override suspend fun placeOrder(): Response<JsonObject> = orderApi.placeOrder()

}