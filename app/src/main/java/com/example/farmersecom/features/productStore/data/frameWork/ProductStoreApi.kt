package com.example.farmersecom.features.productStore.data.frameWork

import com.example.farmersecom.features.productStore.domain.model.storeDetails.StoreDetailsResponse
import com.example.farmersecom.features.productStore.domain.model.storeProducts.StoreProducts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductStoreApi
{





    @GET("api/store/get/{id}")
    suspend fun getStoreById(@Path("id")id:String): Response<StoreDetailsResponse>
    // Response store Id , StoreName, store Desc , Seller Id, seller Name , seller Image




    @GET("api/product/getProductByStoreId")
    suspend fun getStoreProductsByStoreId(@Query("storeId")id:String): Response<StoreProducts>




}