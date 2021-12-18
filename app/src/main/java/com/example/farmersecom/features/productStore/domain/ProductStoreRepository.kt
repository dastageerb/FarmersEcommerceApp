package com.example.farmersecom.features.productStore.domain

import androidx.paging.PagingData
import com.example.farmersecom.features.productStore.domain.model.Product
import com.example.farmersecom.features.productStore.domain.model.StoreDetailsResponse
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ProductStoreRepository
{


    suspend fun getStoreById(id:String): Response<StoreDetailsResponse>


    suspend fun getStoreProductsByStoreId(id:String): Response<List<Product>>



}