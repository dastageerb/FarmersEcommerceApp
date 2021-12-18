package com.example.farmersecom.features.storeAdmin.data.framework

import com.example.farmersecom.features.storeAdmin.data.framework.entities.responses.NewProductResponse
import com.example.farmersecom.features.storeAdmin.domain.model.OrderStatus
import com.example.farmersecom.features.storeAdmin.domain.model.ProductStatus
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface  StoreAdminApi
{


    // com.example.farmersecom.features.productDetails.domain.model.Store Dashboard


    // Add new Product
    @Multipart
    @POST("api/product/add")
    suspend fun addNewProduct(@QueryMap map: MutableMap<String,String>, @Part file: MultipartBody.Part) : Response<NewProductResponse>


    // TODO
    @GET("api/product/")
    suspend fun getProductByStatus(
            @Query("pageNo")pageNo:Int,
            @Query("pageSize")pageSize:Int,
            @Query("isActive")isActive:Boolean): Response<List<ProductStatus>>



    // TODO
    @GET("api/product/")
    suspend fun getOrderByStatus(
        @Query("pageNo")pageNo:Int,
        @Query("pageSize")pageSize:Int,
        @Query("orderStatus")orderStatus:String): Response<List<OrderStatus>>


} // storeAdminApi