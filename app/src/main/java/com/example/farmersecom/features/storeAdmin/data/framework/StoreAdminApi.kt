package com.example.farmersecom.features.storeAdmin.data.framework

import com.example.farmersecom.features.buyerSection.domain.model.orderStatus.OrderStatusResponse
import com.example.farmersecom.features.productStore.domain.model.storeDetails.StoreDetailsResponse
import com.example.farmersecom.features.storeAdmin.data.framework.entities.responses.NewProductResponse
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.example.farmersecom.features.storeAdmin.domain.model.productStatusResponse.ProductStatus
import com.example.farmersecom.features.search.domain.model.categories.CategoriesResponse
import com.example.farmersecom.features.storeAdmin.domain.model.editProduct.EditProduct
import com.example.farmersecom.features.storeAdmin.domain.model.updateStore.UpdateStore
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface  StoreAdminApi
{




    @Multipart
    @POST("api/product/add")
    suspend fun addNewProduct(
        @Part("productName")productName:RequestBody,
        @Part("price")price:Int,
        @Part("quantity")quantity:Int,
        @Part("description")description:RequestBody,
        @Part("unit")unit:RequestBody,
        @Part("category")category:RequestBody,
        @Part("location")location:RequestBody,
        @Part firstFile:MultipartBody.Part,
        @Part secondFile:MultipartBody.Part,
        @Part thirdFile:MultipartBody.Part

    ) : Response<StatusMsgResponse>










    @GET("api/product/getStoreProducts")
    suspend fun getProductByStatus(
        @Query("isActive")isActive:Boolean): Response<List<ProductStatus>>



    @GET("api/store/order/getStoreOrders")
    suspend fun getOrderByStatus(@Query("isActive")orderStatus:Boolean): Response<OrderStatusResponse>


    @POST("api/product/changeProductStatusById")
    suspend fun changeProductStatus(@Query("isActive")status:Boolean
                                    ,@Query("productId")productId:String
    ): Response<StatusMsgResponse>

    @DELETE("api/product/delete/{id}")
    suspend fun deleteProduct(@Path("id")productId:String): Response<StatusMsgResponse>



    @POST("api/store/order/changeOrderStatusById")
    suspend fun changeOrderStatus(@Query("status")status:String,
    @Query("orderId")orderId:String):Response<StatusMsgResponse>



    @GET("api/store")
    suspend fun getStoreDetails(): Response<StoreDetailsResponse>

    @POST("api/store/deliveryOutCity")
    suspend fun editStoreCashOnDelivery(@Query("deliveryOutCity")yesORNo:Boolean): Response<StatusMsgResponse>


    @POST("api/store/edit")
    suspend fun updateStoreInfo(@Body updateStore: UpdateStore)
    : Response<StatusMsgResponse>

    @Multipart
    @POST("api/store/changeStorePhoto")
    suspend fun updateStoreImage(@Part file:MultipartBody.Part) : Response<StatusMsgResponse>



    //

    @POST("api/product/edit/{id}")
    suspend fun editProduct(@Body editProduct: EditProduct,@Path("id")productId:String)
            : Response<StatusMsgResponse>



} // storeAdminApi