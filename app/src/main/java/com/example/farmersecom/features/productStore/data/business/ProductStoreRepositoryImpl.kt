package com.example.farmersecom.features.productStore.data.business

import com.example.farmersecom.features.productStore.data.frameWork.ProductStoreApi
import com.example.farmersecom.features.productStore.domain.ProductStoreRepository

class ProductStoreRepositoryImpl(private val productStoreApi: ProductStoreApi):ProductStoreRepository
{

    override suspend fun getStoreById(id: String)
    =   productStoreApi.getStoreById(id)



    override suspend fun getStoreProductsByStoreId(id: String)
    = productStoreApi.getStoreProductsByStoreId(id)
    //= productStoreApi.getStoreProductsByStoreId(id,1,200)


// = Pager(PagingConfig(20)) { StoreProductsPagingSource(productStoreApi,id) }.flow



} // ProductStoreRepositoryImpl closed