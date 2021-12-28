package com.example.farmersecom.features.storeAdmin.domain.useCases

import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import javax.inject.Inject

class GetStoreDetailsUseCase @Inject constructor(private val storeAdminRepository: StoreAdminRepository)
{

    suspend fun getStoreDetails() = storeAdminRepository.getStoreDetails()

} // addProductUseCase closed