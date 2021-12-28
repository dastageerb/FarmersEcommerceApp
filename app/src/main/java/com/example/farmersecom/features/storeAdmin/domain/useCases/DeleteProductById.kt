package com.example.farmersecom.features.storeAdmin.domain.useCases

import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import javax.inject.Inject

class DeleteProductById @Inject constructor(private val storeAdminRepository: StoreAdminRepository)
{

    suspend fun deleteProductById(productId:String) = storeAdminRepository.deleteProductById(productId)


} // addProductUseCase closed