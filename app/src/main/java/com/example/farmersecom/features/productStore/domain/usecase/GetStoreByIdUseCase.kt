package com.example.farmersecom.features.productStore.domain.usecase

import com.example.farmersecom.features.productStore.data.frameWork.ProductStoreApi
import com.example.farmersecom.features.productStore.domain.ProductStoreRepository
import javax.inject.Inject

class GetStoreByIdUseCase @Inject constructor(private val productStoreRepository: ProductStoreRepository)
{
    suspend fun getProductStoreById(id:String) = productStoreRepository.getStoreById(id)
}