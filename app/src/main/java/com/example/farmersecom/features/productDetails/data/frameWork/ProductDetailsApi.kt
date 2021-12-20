package com.example.farmersecom.features.productDetails.data.frameWork

import com.example.farmersecom.features.productDetails.domain.model.ProductDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailsApi
{

//    // TODO
//    @GET("api/product/get/61b3062c658dc50016921ad0")
//    suspend fun getProductById(@Query("id")id:String):Response<JsonObject>

    @GET("api/product/get/{id}")
    suspend fun getProductById(@Path("id")id:String):Response<ProductDetailsResponse>

//
//    @GET("/photos/{id}")
//    suspend fun getPhotoDetails(@Path("id")id:String) : Response<PhotoResponse>
//


}