package com.example.farmersecom.features.productDetails.domain

import com.google.gson.JsonObject
import retrofit2.Response

interface ProductDetailsRepository
{

    suspend fun getProductById(id:String):Response<JsonObject>


}