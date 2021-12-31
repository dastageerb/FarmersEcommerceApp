package com.example.farmersecom.features.storeAdmin.data.business


import com.example.farmersecom.features.buyerSection.domain.model.orderStatus.OrderStatusResponse
import com.example.farmersecom.features.productStore.domain.model.storeDetails.StoreDetailsResponse
import com.example.farmersecom.features.storeAdmin.data.framework.StoreAdminApi
import com.example.farmersecom.features.storeAdmin.data.framework.entities.requests.NewProduct
import com.example.farmersecom.features.storeAdmin.data.framework.entities.responses.NewProductResponse
import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.example.farmersecom.features.storeAdmin.domain.model.editProduct.EditProduct
import com.example.farmersecom.features.storeAdmin.domain.model.updateStore.UpdateStore
import okhttp3.MultipartBody
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody

import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class StoreAdminImpl(private val storeAdminApi:StoreAdminApi) : StoreAdminRepository
{


    override suspend fun addNewProduct(newProduct: NewProduct
                                       ,firstFile:MultipartBody.Part
                                       ,secondFile:MultipartBody.Part
                                       ,thirdFile:MultipartBody.Part):Response<StatusMsgResponse>
    {

        val categoryReqBody = newProduct.productCategory.toRequestBody("text/plain".toMediaTypeOrNull())
        val nameReqBody = newProduct.productName.toRequestBody("text/plain".toMediaTypeOrNull())
        val unitReqBody = newProduct.productUnit.toRequestBody("text/plain".toMediaTypeOrNull())
        val descReqBody = newProduct.productDescription.toRequestBody("text/plain".toMediaTypeOrNull())
        val locationReqBody = newProduct.productLocation.toRequestBody("text/plain".toMediaTypeOrNull())


        return  storeAdminApi
            .addNewProduct(
                nameReqBody
                ,newProduct.productPrice
                ,newProduct.productQuantity
                ,descReqBody
               ,unitReqBody
                ,categoryReqBody
                ,locationReqBody,firstFile,secondFile,thirdFile)
    } // add new product closed




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

    override suspend fun getStoreDetails(): Response<StoreDetailsResponse>

    =    storeAdminApi.getStoreDetails()


    override suspend fun editStoreCashOnDelivery(yesORNo: Boolean): Response<StatusMsgResponse>
    = storeAdminApi.editStoreCashOnDelivery(yesORNo)

    override suspend fun updateStoreInfo(name: String, desc: String): Response<StatusMsgResponse>

      =   storeAdminApi.updateStoreInfo(UpdateStore(desc,name))

    override suspend fun editProduct(editProduct: EditProduct,productId: String)
     = storeAdminApi.editProduct(editProduct,productId)

    override suspend fun updateStoreImage(file: MultipartBody.Part)
     = storeAdminApi.updateStoreImage(file)


} // ProfileRepoImpl