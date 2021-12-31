package com.example.farmersecom.features.storeAdmin.domain.useCases

import com.example.farmersecom.features.storeAdmin.data.framework.entities.requests.NewProduct
import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class AddProductUseCase @Inject constructor(private val storeAdminRepository: StoreAdminRepository)
{

    suspend fun addNewProduct(newProduct: NewProduct
                              ,firstFile: MultipartBody.Part
                              ,secondFile: MultipartBody.Part
                              ,thirdFile: MultipartBody.Part) = storeAdminRepository
        .addNewProduct(newProduct,firstFile,secondFile,thirdFile)

} // addProductUseCase closed