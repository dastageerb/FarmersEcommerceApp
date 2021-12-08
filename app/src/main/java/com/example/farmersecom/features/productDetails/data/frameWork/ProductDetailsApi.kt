package com.example.farmersecom.features.productDetails.data.frameWork

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductDetailsApi
{

    // TODO
    @GET("")
    suspend fun getProductById(@Query("id")id:String):Response<JsonObject>

}