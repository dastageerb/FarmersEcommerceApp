package com.example.farmersecom.features.storeAdmin.data.business


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.farmersecom.SharedPrefsHelper_Factory.create
import com.example.farmersecom.features.storeAdmin.data.framework.StoreAdminApi
import com.example.farmersecom.features.storeAdmin.data.framework.entities.NewProduct
import com.example.farmersecom.features.storeAdmin.data.framework.entities.responses.NewProductResponse
import com.example.farmersecom.features.storeAdmin.data.framework.pagingSource.ProductsByStatusPagingSource
import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import com.example.farmersecom.features.storeAdmin.domain.model.OrderStatus
import com.example.farmersecom.features.storeAdmin.domain.model.ProductStatus
import com.example.farmersecom.features.storeAdmin.domain.model.categories.CategoriesResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull

import okhttp3.RequestBody
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
               // ,newProduct.productUnit,
                ,reqBody,newProduct.productLocation,file)
    }


    override suspend fun getProductByStatus(isActive:Boolean): Flow<PagingData<ProductStatus>>
      = Pager(PagingConfig(20)) { ProductsByStatusPagingSource(isActive,storeAdminApi) }.flow


    override suspend fun getOrdersByStatus(orderStatus:Boolean)
    = storeAdminApi.getOrderByStatus(orderStatus)


} // ProfileRepoImpl