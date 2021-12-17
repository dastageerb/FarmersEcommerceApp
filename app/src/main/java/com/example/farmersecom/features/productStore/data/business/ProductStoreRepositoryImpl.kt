package com.example.farmersecom.features.productStore.data.business

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.farmersecom.features.productStore.data.frameWork.ProductStoreApi
import com.example.farmersecom.features.productStore.data.frameWork.StoreProductsPagingSource
import com.example.farmersecom.features.productStore.domain.ProductStoreRepository
import com.example.farmersecom.features.productStore.domain.model.Product
import com.example.farmersecom.features.search.data.business.SearchPagingSource
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class ProductStoreRepositoryImpl(private val productStoreApi: ProductStoreApi):ProductStoreRepository
{

    override suspend fun getStoreById(id: String): Response<JsonObject>
    {
        return  productStoreApi.getStoreById(id)
    }


    override suspend fun getStoreProductsByStoreId(id: String): Flow<PagingData<Product>>
    = Pager(PagingConfig(20)) { StoreProductsPagingSource(productStoreApi,id) }.flow



} // ProductStoreRepositoryImpl closed