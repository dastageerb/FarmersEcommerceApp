package com.example.farmersecom.features.productStore.domain

import androidx.paging.PagingData
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ProductStoreRepository
{


    suspend fun getStoreById(id:String): Response<JsonObject>


    suspend fun getStoreProductsByStoreId(id:String): Flow<PagingData<JsonObject>>



}