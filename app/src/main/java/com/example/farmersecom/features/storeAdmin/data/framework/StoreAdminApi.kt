package com.example.farmersecom.features.storeAdmin.data.framework

import com.example.farmersecom.features.buyerSection.domain.model.orderStatus.OrderStatusResponse
import com.example.farmersecom.features.productStore.domain.model.storeDetails.StoreDetailsResponse
import com.example.farmersecom.features.storeAdmin.data.framework.entities.responses.NewProductResponse
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.example.farmersecom.features.storeAdmin.domain.model.productStatusResponse.ProductStatus
import com.example.farmersecom.features.storeAdmin.domain.model.categories.CategoriesResponse
import com.example.farmersecom.features.storeAdmin.domain.model.changeStoreImage.ChangeStoreImageResponse
import com.example.farmersecom.features.storeAdmin.domain.model.updateStore.UpdateStore
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface  StoreAdminApi
{


    // com.example.farmersecom.features.productDetails.domain.model.Store Dashboard


    //{language_code}/{word}
    // Add new Product
    @Multipart
    @POST("api/product/add")
    suspend fun addNewProduct(
        @Part("productName")productName:String,
        @Part("price")price:Int,
        @Part("quantity")quantity:Int,
        @Part("description")description:String,
        @Part("unit")unit:String,
        @Part("category")category:RequestBody,
        @Part("location")location:String,
        @Part file:MultipartBody.Part

    ) : Response<NewProductResponse>


    @GET("api/category/getCategory")
    suspend fun getAllCategories():Response<CategoriesResponse>









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


    //@GET // by auth
    suspend fun getStoreDetails(): Response<StoreDetailsResponse>

    //@POST
    suspend fun editStoreCashOnDelivery(yesORNo:Boolean): Response<StatusMsgResponse>


    @POST("api/store/edit")
    suspend fun updateStoreInfo(@Body updateStore: UpdateStore)
    : Response<StatusMsgResponse>

    @Multipart
    @PUT("api/store/changeStorePhoto")
    suspend fun updateStoreImage(@Part file:MultipartBody.Part) : Response<ChangeStoreImageResponse>






} // storeAdminApi