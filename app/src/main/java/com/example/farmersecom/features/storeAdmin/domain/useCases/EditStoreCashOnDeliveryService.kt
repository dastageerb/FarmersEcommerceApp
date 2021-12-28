package com.example.farmersecom.features.storeAdmin.domain.useCases

import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import javax.inject.Inject

class EditStoreCashOnDeliveryService @Inject constructor(private val storeAdminRepository: StoreAdminRepository)
{

    suspend fun editCashOnDeliveryService(boolean: Boolean) = storeAdminRepository.editStoreCashOnDelivery(boolean)

} // addProductUseCase closed