package com.example.farmersecom.features.storeAdmin.domain.useCases

import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import javax.inject.Inject

class UpdateStoreUseCase @Inject constructor(private val storeAdminRepository: StoreAdminRepository)
{

    suspend fun updateStoreUseCase(name:String,desc:String) = storeAdminRepository.updateStoreInfo(name,desc)
} // addProductUseCase closed