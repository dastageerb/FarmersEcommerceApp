package com.example.farmersecom.features.storeAdmin.domain.useCases

import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import javax.inject.Inject

class ChangeOrderStatusUseCase @Inject constructor(private val storeAdminRepository: StoreAdminRepository)
{

    suspend fun changeOrderStatus(status:String,orderId:String)
    = storeAdminRepository.changeOrderStatus(status,orderId)


} // addProductUseCase closed