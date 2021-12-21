package com.example.farmersecom.features.buyerSection.domain

import androidx.paging.PagingData
import com.example.farmersecom.features.buyerSection.domain.model.orderDetails.OrderDetailsResponse
import com.example.farmersecom.features.buyerSection.domain.model.orderStatus.OrderStatusResponse
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface BuyerDashboardRepository
{



    suspend fun getOrdersByStatus(orderStatus:Boolean) : Response<OrderStatusResponse>

    suspend fun getOrderDetailsById(orderId:String) : Response<OrderDetailsResponse>




    suspend fun getBuyerNotifications() : Flow<PagingData<JsonObject>>


    suspend fun getBuyerFavourites() : Flow<PagingData<JsonObject>>








}