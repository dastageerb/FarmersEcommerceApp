package com.example.farmersecom.features.productDetails.domain

import com.example.farmersecom.features.productDetails.domain.model.ProductDetailsResponse
import com.google.gson.JsonObject
import retrofit2.Response

interface ProductDetailsRepository
{

    suspend fun getProductById(id:String):Response<ProductDetailsResponse>


}