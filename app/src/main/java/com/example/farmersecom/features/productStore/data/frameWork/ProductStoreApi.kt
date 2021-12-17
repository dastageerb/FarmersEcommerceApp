package com.example.farmersecom.features.productStore.data.frameWork

import com.example.farmersecom.features.productStore.domain.model.Product
import com.example.farmersecom.features.productStore.domain.model.StoreProductsResponse
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




    @GET("api/product/getProductByStoreId")
    suspend fun getStoreProductsByStoreId(@Query("storeId")id:String,
                                          @Query("page")pageNo:Int,
                                          @Query("size")PageSize:Int): Response<List<Product>>




}