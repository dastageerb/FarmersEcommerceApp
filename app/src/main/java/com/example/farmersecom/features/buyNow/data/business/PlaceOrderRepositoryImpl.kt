package com.example.farmersecom.features.buyNow.data.business

import com.example.farmersecom.features.buyNow.data.framwork.OrderApi
import com.example.farmersecom.features.buyNow.domain.PlaceOrderRepository
import com.example.farmersecom.features.buyNow.domain.model.request.OrderRequest
import com.example.farmersecom.features.buyNow.domain.model.response.OrderResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body

class PlaceOrderRepositoryImpl(private val orderApi: OrderApi) : PlaceOrderRepository
{
    override suspend fun placeOrder(productId:String,orderRequest: OrderRequest):
            Response<OrderResponse> = orderApi.placeOrder(productId,orderRequest)

}