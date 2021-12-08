package com.example.farmersecom.features.productDetails.data.business

import com.example.farmersecom.features.productDetails.data.frameWork.ProductDetailsApi
import com.example.farmersecom.features.productDetails.domain.ProductDetailsRepository
import com.google.gson.JsonObject
import retrofit2.Response

class ProductDetailsImpl(private val productDetailsApi: ProductDetailsApi):ProductDetailsRepository
{
    override suspend fun getProductById(id: String): Response<JsonObject>
    = productDetailsApi.getProductById(id)


}