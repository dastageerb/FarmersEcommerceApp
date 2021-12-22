package com.example.farmersecom.features.storeAdmin.domain.useCases

import com.example.farmersecom.features.storeAdmin.data.framework.entities.NewProduct
import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class ChangeOrderStatusUseCase @Inject constructor(private val storeAdminRepository: StoreAdminRepository)
{

    suspend fun changeOrderStatus(status:String,orderId:String)
    = storeAdminRepository.changeOrderStatus(status,orderId)


} // addProductUseCase closed