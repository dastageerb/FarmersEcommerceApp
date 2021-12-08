package com.example.farmersecom.features.buyerSection.data.framework

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BuyerDashboardApi
{

    // TODO
    @GET("")
    suspend fun getOrdersByStatus(@Query("orderStatus")orderStatus:String,
                                  @Query("pageNo")pageNo:Int,
                                  @Query("pageSize")PageSize:Int) :Response<List<JsonObject>>

    // TODO
    @GET("")
    suspend fun getBuyerNotification( @Query("pageNo")pageNo:Int,
                                      @Query("pageSize")PageSize:Int) :Response<List<JsonObject>>

    // TODO
    @GET("")
    suspend fun getBuyerFavourites( @Query("pageNo")pageNo:Int,
                                    @Query("pageSize")PageSize:Int) :Response<List<JsonObject>>



} // BuyerDashboardApi closed