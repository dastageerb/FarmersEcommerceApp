package com.example.farmersecom.features.storeAdmin.domain.useCases

import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class UpdateStoreImageUseCase @Inject constructor(private val storeAdminRepository: StoreAdminRepository)
{

    suspend fun updateStoreImageUseCase(file:MultipartBody.Part) = storeAdminRepository.updateStoreImage(file)

} // addProductUseCase closed