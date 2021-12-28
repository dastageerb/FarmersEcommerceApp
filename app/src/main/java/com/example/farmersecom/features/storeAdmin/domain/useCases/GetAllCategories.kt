package com.example.farmersecom.features.storeAdmin.domain.useCases

import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import javax.inject.Inject

class GetAllCategories @Inject constructor(private val storeAdminRepository: StoreAdminRepository)
{

    suspend fun getAllCategories() = storeAdminRepository.getAllCategories()

} // addProductUseCase closed