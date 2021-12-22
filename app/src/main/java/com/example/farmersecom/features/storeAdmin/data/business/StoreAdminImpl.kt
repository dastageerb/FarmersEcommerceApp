package com.example.farmersecom.features.storeAdmin.data.business


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.farmersecom.features.storeAdmin.data.framework.StoreAdminApi
import com.example.farmersecom.features.storeAdmin.data.framework.entities.NewProduct
import com.example.farmersecom.features.storeAdmin.data.framework.entities.responses.NewProductResponse
import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.example.farmersecom.features.storeAdmin.domain.model.productStatusResponse.ProductStatus
import com.example.farmersecom.features.storeAdmin.domain.model.categories.CategoriesResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull

import okhttp3.RequestBody.Companion.toRequestBody


class StoreAdminImpl(private val storeAdminApi:StoreAdminApi) : StoreAdminRepository
{
    override suspend fun getAllCategories(): Response<CategoriesResponse>
    = storeAdminApi.getAllCategories()

    override suspend fun addNewProduct(newProduct:NewProduct, file: MultipartBody.Part): Response<NewProductResponse>
    {

        val reqBody = newProduct.productCategory.toRequestBody("text/plain".toMediaTypeOrNull())
        return  storeAdminApi
            .addNewProduct(
                newProduct.productName
                ,newProduct.productPrice
                ,newProduct.productQuantity
                ,newProduct.productDescription
               ,newProduct.productUnit
                ,reqBody
                ,newProduct.productLocation,file)
    }


    override suspend fun getProductsByStatus(isActive:Boolean)
    = storeAdminApi.getProductByStatus(isActive)

    // = Pager(PagingConfig(20)) { ProductsByStatusPagingSource(isActive,storeAdminApi) }.flow


    override suspend fun getOrdersByStatus(orderStatus:Boolean)
    = storeAdminApi.getOrderByStatus(orderStatus)

    override suspend fun changeProductStatus(status: Boolean, productId: String): Response<StatusMsgResponse>
    = storeAdminApi.changeProductStatus(status,productId)

    override suspend fun deleteProductById(productId: String): Response<StatusMsgResponse>
    = storeAdminApi.deleteProduct(productId)

    override suspend fun changeOrderStatus(status: String, orderId: String): Response<StatusMsgResponse>
    = storeAdminApi.changeOrderStatus(status,orderId)


} // ProfileRepoImpl