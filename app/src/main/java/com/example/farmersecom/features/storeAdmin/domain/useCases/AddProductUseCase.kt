package com.example.farmersecom.features.storeAdmin.domain.useCases

import com.example.farmersecom.features.storeAdmin.data.framework.entities.requests.NewProduct
import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class AddProductUseCase @Inject constructor(private val storeAdminRepository: StoreAdminRepository)
{

    suspend fun addNewProduct(newProduct: NewProduct, file: MultipartBody.Part) = storeAdminRepository.addNewProduct(newProduct,file)

} // addProductUseCase closed