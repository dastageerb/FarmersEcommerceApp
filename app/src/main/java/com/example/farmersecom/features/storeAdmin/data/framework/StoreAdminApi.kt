package com.example.farmersecom.features.storeAdmin.data.framework

import com.example.farmersecom.features.buyerSection.domain.model.orderStatus.OrderStatusResponse
import com.example.farmersecom.features.productDetails.domain.model.ProductPicture
import com.example.farmersecom.features.storeAdmin.data.framework.entities.responses.NewProductResponse
import com.example.farmersecom.features.storeAdmin.domain.model.ProductStatus
import com.example.farmersecom.features.storeAdmin.domain.model.categories.CategoriesResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonQualifier
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*
import java.util.zip.ZipEntry

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
       // @Part("unit")unit:String,
        @Part("category")category:RequestBody,
        @Part("location")location:String,
        @Part file:MultipartBody.Part

    ) : Response<NewProductResponse>


    @GET("api/category/getCategory")
    suspend fun getAllCategories():Response<CategoriesResponse>






    @Multipart
    @POST("api/product/add")
    suspend fun uploadProduct(
        @Part("productName")productName:String,
        @Part("price")price:Int,
        @Part("quantity")quantity:Int,
        @Part("description")description:String,
        @Part("unit")unit:String,
        @Part("category")category:String,
        @Part("location")location:String
    ) : Response<NewProductResponse>



    // TODO
    @GET("api/product/")
    suspend fun getProductByStatus(
            @Query("pageNo")pageNo:Int,
            @Query("pageSize")pageSize:Int,
            @Query("isActive")isActive:Boolean): Response<List<ProductStatus>>



    // TODO
    @GET("api/store/order/getStoreOrders")
    suspend fun getOrderByStatus(@Query("isActive")orderStatus:Boolean): Response<OrderStatusResponse>


} // storeAdminApi