package com.example.farmersecom.features.buyerSection.data.framework

import com.example.farmersecom.features.buyerSection.domain.model.OrderStatusResponse
import com.example.farmersecom.features.storeAdmin.domain.model.OrderStatus
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BuyerDashboardApi
{


    @GET("api/buyer/order/getBuyerOrders")
    suspend fun getOrdersByStatus(@Query("isActive")orderStatus:Boolean,
                                ) :Response<OrderStatusResponse>


    @GET("api/buyer/order/getBuyerOrders")
    suspend fun getOrders(@Query("isActive")orderStatus:Boolean,
    ) :Response<Any>


    // TODO
    @GET("")
    suspend fun getBuyerNotification( @Query("pageNo")pageNo:Int,
                                      @Query("pageSize")PageSize:Int) :Response<List<JsonObject>>

    // TODO
    @GET("")
    suspend fun getBuyerFavourites( @Query("pageNo")pageNo:Int,
                                    @Query("pageSize")PageSize:Int) :Response<List<JsonObject>>



} // BuyerDashboardApi closed