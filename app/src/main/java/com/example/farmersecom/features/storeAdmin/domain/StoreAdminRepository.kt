package com.example.farmersecom.features.storeAdmin.domain

import androidx.paging.PagingData
import com.example.farmersecom.features.buyerSection.domain.model.orderStatus.OrderStatusResponse
import com.example.farmersecom.features.storeAdmin.data.framework.entities.NewProduct
import com.example.farmersecom.features.storeAdmin.data.framework.entities.responses.NewProductResponse
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.example.farmersecom.features.storeAdmin.domain.model.productStatusResponse.ProductStatus
import com.example.farmersecom.features.storeAdmin.domain.model.categories.CategoriesResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response

interface StoreAdminRepository
{

    suspend fun getAllCategories():Response<CategoriesResponse>

    suspend fun addNewProduct(newProduct: NewProduct, file: MultipartBody.Part) :Response<NewProductResponse>

    suspend fun getProductsByStatus(isActive:Boolean): Response<List<ProductStatus>>

    suspend fun getOrdersByStatus(orderStatus:Boolean): Response<OrderStatusResponse>

    suspend fun changeProductStatus(status:Boolean,productId:String):Response<StatusMsgResponse>

    suspend fun deleteProductById(productId:String):Response<StatusMsgResponse>


    suspend fun changeOrderStatus(status:String,orderId:String):Response<StatusMsgResponse>




} //