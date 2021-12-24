package com.example.farmersecom.features.storeAdmin.domain

import androidx.paging.PagingData
import com.example.farmersecom.features.buyerSection.domain.model.orderStatus.OrderStatusResponse
import com.example.farmersecom.features.productStore.domain.model.storeDetails.StoreDetailsResponse
import com.example.farmersecom.features.profile.data.framework.entities.ChangePhotoNetworkEntity
import com.example.farmersecom.features.storeAdmin.data.framework.entities.NewProduct
import com.example.farmersecom.features.storeAdmin.data.framework.entities.responses.NewProductResponse
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.example.farmersecom.features.storeAdmin.domain.model.productStatusResponse.ProductStatus
import com.example.farmersecom.features.storeAdmin.domain.model.categories.CategoriesResponse
import com.example.farmersecom.features.storeAdmin.domain.model.changeStoreImage.ChangeStoreImageResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface StoreAdminRepository
{

    suspend fun getAllCategories():Response<CategoriesResponse>

    suspend fun addNewProduct(newProduct: NewProduct, file: MultipartBody.Part) :Response<NewProductResponse>

    suspend fun getProductsByStatus(isActive:Boolean): Response<List<ProductStatus>>

    suspend fun getOrdersByStatus(orderStatus:Boolean): Response<OrderStatusResponse>

    suspend fun changeProductStatus(status:Boolean,productId:String):Response<StatusMsgResponse>

    suspend fun deleteProductById(productId:String):Response<StatusMsgResponse>


    suspend fun changeOrderStatus(status:String,orderId:String):Response<StatusMsgResponse>



    suspend fun getStoreDetails(): Response<StoreDetailsResponse>

    suspend fun editStoreCashOnDelivery(yesORNo:Boolean): Response<StatusMsgResponse>


    suspend fun updateStoreInfo(name:String,desc:String)
            : Response<StatusMsgResponse>

    suspend fun updateStoreImage(file:MultipartBody.Part) : Response<ChangeStoreImageResponse>


} //