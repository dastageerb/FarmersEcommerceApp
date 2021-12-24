package com.example.farmersecom.features.storeAdmin.domain.useCases

import com.example.farmersecom.features.storeAdmin.data.framework.entities.NewProduct
import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class UpdateStoreUseCase @Inject constructor(private val storeAdminRepository: StoreAdminRepository)
{

    suspend fun updateStoreUseCase(name:String,desc:String) = storeAdminRepository.updateStoreInfo(name,desc)
} // addProductUseCase closed