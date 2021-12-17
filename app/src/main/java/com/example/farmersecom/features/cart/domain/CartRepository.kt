package com.example.farmersecom.features.cart.domain

import androidx.paging.PagingData
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

interface CartRepository
{


    suspend fun getCartItems():Flow<PagingData<JsonObject>>



}