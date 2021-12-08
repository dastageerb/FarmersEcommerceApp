package com.example.farmersecom.features.storeAdmin.data.business


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.farmersecom.features.storeAdmin.data.framework.StoreAdminApi
import com.example.farmersecom.features.storeAdmin.data.framework.entities.responses.NewProductResponse
import com.example.farmersecom.features.storeAdmin.data.framework.pagingSource.OrderByStatusPagingSource
import com.example.farmersecom.features.storeAdmin.data.framework.pagingSource.ProductsByStatusPagingSource
import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import com.example.farmersecom.features.storeAdmin.domain.model.OrderStatus
import com.example.farmersecom.features.storeAdmin.domain.model.ProductStatus
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response


class StoreAdminImpl(private val storeAdminApi:StoreAdminApi) : StoreAdminRepository
{

    override suspend fun addNewProduct(map: MutableMap<String,String>, file: MultipartBody.Part):Response<NewProductResponse>
    {
        return storeAdminApi.addNewProduct(map,file)
    }

    override suspend fun getProductByStatus(isActive:Boolean): Flow<PagingData<ProductStatus>>
      = Pager(PagingConfig(20)) { ProductsByStatusPagingSource(isActive,storeAdminApi) }.flow


    override suspend fun getOrdersByStatus(orderStatus: String): Flow<PagingData<OrderStatus>>
    = Pager(PagingConfig(20)) { OrderByStatusPagingSource(orderStatus,storeAdminApi) }.flow


} // ProfileRepoImpl