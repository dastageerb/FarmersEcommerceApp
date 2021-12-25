package com.example.farmersecom.features.buyerSection.domain

import androidx.paging.PagingData
import com.example.farmersecom.features.buyerSection.domain.model.orderDetails.OrderDetailsResponse
import com.example.farmersecom.features.buyerSection.domain.model.orderStatus.OrderStatusResponse
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BuyerDashboardRepository
{



    suspend fun getOrdersByStatus(orderStatus:Boolean) : Response<OrderStatusResponse>

    suspend fun getOrderDetailsById(orderId:String) : Response<OrderDetailsResponse>


    suspend fun getBuyerNotifications() : Flow<PagingData<JsonObject>>


    suspend fun getBuyerFavourites() : Flow<PagingData<JsonObject>>



    suspend fun rateProduct(productId:String,rating:Float)
            :Response<StatusMsgResponse>

    suspend fun cancelOrder(orderId:String)
            :Response<StatusMsgResponse>


    suspend fun addProductToFavourites(productId:String)
            :Response<StatusMsgResponse>

    suspend fun removeProductFromFavourites(productId:String)
            :Response<StatusMsgResponse>





}