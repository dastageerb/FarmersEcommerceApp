package com.example.farmersecom.features.productStore.domain

import com.example.farmersecom.features.productStore.domain.model.storeDetails.StoreDetailsResponse
import com.example.farmersecom.features.productStore.domain.model.storeProducts.StoreProducts
import retrofit2.Response

interface ProductStoreRepository
{


    suspend fun getStoreById(id:String): Response<StoreDetailsResponse>


    suspend fun getStoreProductsByStoreId(id:String): Response<StoreProducts>



}