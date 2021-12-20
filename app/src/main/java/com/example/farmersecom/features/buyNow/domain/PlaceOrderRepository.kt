package com.example.farmersecom.features.buyNow.domain

import com.example.farmersecom.features.buyNow.domain.model.request.OrderRequest
import com.example.farmersecom.features.buyNow.domain.model.response.OrderResponse
import com.google.gson.JsonObject
import retrofit2.Response

interface PlaceOrderRepository
{

    suspend fun placeOrder(productId:String,orderRequest: OrderRequest):
            Response<OrderResponse>


}