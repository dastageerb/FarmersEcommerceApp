package com.example.farmersecom.features.productStore.data.frameWork

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductStoreApi
{

    // TODO
    @GET("")
    suspend fun getStoreById(@Query("storeId")id:String): Response<JsonObject>
    // Response store Id , StoreName, store Desc , Seller Id, seller Name , seller Image



    // Response All the products in that store with pagination
    // TODO
    @GET("")
    suspend fun getStoreProductsByStoreId(@Query("storeId")id:String,
                                          @Query("pageNo")pageNo:Int,
                                          @Query("pageSize")PageSize:Int): Response<List<JsonObject>>




}