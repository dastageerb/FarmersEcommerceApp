package com.example.farmersecom.features.storeAdmin.domain.useCases

import com.example.farmersecom.features.storeAdmin.data.framework.entities.NewProduct
import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class DeleteProductById @Inject constructor(private val storeAdminRepository: StoreAdminRepository)
{

    suspend fun deleteProductById(productId:String) = storeAdminRepository.deleteProductById(productId)


} // addProductUseCase closed