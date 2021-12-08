package com.example.farmersecom.features.storeAdmin.domain

import androidx.paging.PagingData
import com.example.farmersecom.features.storeAdmin.data.framework.entities.responses.NewProductResponse
import com.example.farmersecom.features.storeAdmin.domain.model.OrderStatus
import com.example.farmersecom.features.storeAdmin.domain.model.ProductStatus
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response

interface StoreAdminRepository
{

    suspend fun addNewProduct(map: MutableMap<String,String>, file: MultipartBody.Part) :Response<NewProductResponse>

    suspend fun getProductByStatus(isActive:Boolean): Flow<PagingData<ProductStatus>>

    suspend fun getOrdersByStatus(orderStatus:String): Flow<PagingData<OrderStatus>>

} //