package com.example.farmersecom.features.storeAdmin.domain.useCases

import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import javax.inject.Inject

class GetProductsByStatusUseCase @Inject constructor(private val storeAdminRepository: StoreAdminRepository)
{

    suspend fun getProductsByStatus(isActive:Boolean) = storeAdminRepository.getProductsByStatus(isActive)


} // addProductUseCase closed