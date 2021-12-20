package com.example.farmersecom.features.buyNow.domain.usecase

import com.example.farmersecom.features.buyNow.domain.PlaceOrderRepository
import com.example.farmersecom.features.buyNow.domain.model.request.OrderRequest
import com.example.farmersecom.features.buyNow.domain.model.response.OrderResponse
import retrofit2.Response
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(private val placeOrderRepository: PlaceOrderRepository)
{
    suspend fun placeOrderUseCase(productId:String,orderRequest: OrderRequest):
            Response<OrderResponse> = placeOrderRepository.placeOrder(productId,orderRequest)

}