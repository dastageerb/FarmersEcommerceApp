package com.example.farmersecom.features.buyerSection.data.framework

import com.example.farmersecom.features.buyerSection.domain.model.orderDetails.OrderDetailsResponse
import com.example.farmersecom.features.buyerSection.domain.model.orderStatus.OrderStatusResponse
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface BuyerDashboardApi
{


    @GET("api/buyer/order/getBuyerOrders")
    suspend fun getOrdersByStatus(@Query("isActive")orderStatus:Boolean,
                                ) :Response<OrderStatusResponse>

    @GET("api/order/get/{id}")
    suspend fun getOrderDetailsById(@Path("id")id:String):Response<OrderDetailsResponse>




    @PUT("api/product/rating")
    suspend fun rateProduct(@Query("productId")id:String,@Query("rating")rating:Float)
    :Response<StatusMsgResponse>

    @POST("api/order/cancel/{id}")
    suspend fun cancelOrder(@Path("id")orderId:String)
            :Response<StatusMsgResponse>


    @POST("api/product/addToFav/{id}")
    suspend fun addProductToFavourites(@Path("id")orderId:String)
            :Response<StatusMsgResponse>

   // @POST("api/product/addToFav/{id}")
    suspend fun removeProductFromFavourites(@Path("id")orderId:String)
            :Response<StatusMsgResponse>






    // TODO
    @GET("")
    suspend fun getBuyerNotification( @Query("pageNo")pageNo:Int,
                                      @Query("pageSize")PageSize:Int) :Response<List<JsonObject>>

    // TODO
    @GET("")
    suspend fun getBuyerFavourites( @Query("pageNo")pageNo:Int,
                                    @Query("pageSize")PageSize:Int) :Response<List<JsonObject>>







} // BuyerDashboardApi closed