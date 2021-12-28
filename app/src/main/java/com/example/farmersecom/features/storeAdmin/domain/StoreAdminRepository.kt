package com.example.farmersecom.features.storeAdmin.domain

import com.example.farmersecom.features.buyerSection.domain.model.orderStatus.OrderStatusResponse
import com.example.farmersecom.features.productStore.domain.model.storeDetails.StoreDetailsResponse
import com.example.farmersecom.features.storeAdmin.data.framework.entities.requests.NewProduct
import com.example.farmersecom.features.storeAdmin.data.framework.entities.responses.NewProductResponse
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.example.farmersecom.features.storeAdmin.domain.model.productStatusResponse.ProductStatus
import com.example.farmersecom.features.storeAdmin.domain.model.editProduct.EditProduct
import okhttp3.MultipartBody
import retrofit2.Response

interface StoreAdminRepository
{


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


    suspend fun editProduct(editProduct: EditProduct,productId: String)
            : Response<StatusMsgResponse>

    suspend fun updateStoreImage(file:MultipartBody.Part) : Response<StatusMsgResponse>


} //