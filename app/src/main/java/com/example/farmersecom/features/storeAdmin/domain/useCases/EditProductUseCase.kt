package com.example.farmersecom.features.storeAdmin.domain.useCases

import com.example.farmersecom.features.storeAdmin.data.framework.entities.requests.NewProduct
import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import com.example.farmersecom.features.storeAdmin.domain.model.editProduct.EditProduct
import okhttp3.MultipartBody
import javax.inject.Inject

class EditProductUseCase @Inject constructor(private val storeAdminRepository: StoreAdminRepository)
{

    suspend fun editProduct(editProduct: EditProduct,productId:String) = storeAdminRepository.editProduct(editProduct,productId)

} // addProductUseCase closed