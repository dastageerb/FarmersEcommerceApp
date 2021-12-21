package com.example.farmersecom.features.buyerSection.data.framework

import com.example.farmersecom.features.buyerSection.domain.model.orderDetails.OrderDetailsResponse
import com.example.farmersecom.features.buyerSection.domain.model.orderStatus.OrderStatusResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BuyerDashboardApi
{


    @GET("api/buyer/order/getBuyerOrders")
    suspend fun getOrdersByStatus(@Query("isActive")orderStatus:Boolean,
                                ) :Response<OrderStatusResponse>

    @GET("api/order/get/{id}")
    suspend fun getOrderDetailsById(@Path("id")id:String):Response<OrderDetailsResponse>



    // TODO
    @GET("")
    suspend fun getBuyerNotification( @Query("pageNo")pageNo:Int,
                                      @Query("pageSize")PageSize:Int) :Response<List<JsonObject>>

    // TODO
    @GET("")
    suspend fun getBuyerFavourites( @Query("pageNo")pageNo:Int,
                                    @Query("pageSize")PageSize:Int) :Response<List<JsonObject>>



} // BuyerDashboardApi closed