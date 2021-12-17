package com.example.farmersecom.features.cart.data.framework

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CartApi
{


    // TODO
    @GET("")
    suspend fun getCartItems(@Query("pageNo")pageNo:Int,@Query("pageSize")pageSize:Int):Response<List<JsonObject>>



}