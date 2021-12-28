package com.example.farmersecom.features.storeAdmin.domain.useCases

import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import javax.inject.Inject

class GetOrdersByStatusUseCase @Inject constructor(private val storeAdminRepository: StoreAdminRepository)
{

    suspend fun getProductsByStatus(orderStatus:Boolean) = storeAdminRepository.getOrdersByStatus(orderStatus)


} // addProductUseCase closed