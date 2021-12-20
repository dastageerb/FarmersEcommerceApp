package com.example.farmersecom.features.buyerSection.domain

import androidx.paging.PagingData
import com.example.farmersecom.features.buyerSection.domain.model.OrderStatusResponse
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BuyerDashboardRepository
{



    suspend fun getOrdersByStatus(orderStatus:Boolean) : Response<OrderStatusResponse>


    suspend fun getBuyerNotifications() : Flow<PagingData<JsonObject>>


    suspend fun getBuyerFavourites() : Flow<PagingData<JsonObject>>








}