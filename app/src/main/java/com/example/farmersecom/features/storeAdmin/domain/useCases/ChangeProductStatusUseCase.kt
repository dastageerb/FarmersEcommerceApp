package com.example.farmersecom.features.storeAdmin.domain.useCases

import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import javax.inject.Inject

class ChangeProductStatusUseCase @Inject constructor(private val storeAdminRepository: StoreAdminRepository)
{

    suspend fun changeProductStatus(status:Boolean,productId:String)
    = storeAdminRepository.changeProductStatus(status,productId)


} // addProductUseCase closed