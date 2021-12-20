package com.example.farmersecom.features.buyNow.data.framwork

import com.example.farmersecom.features.buyNow.domain.model.request.OrderRequest
import com.example.farmersecom.features.buyNow.domain.model.response.OrderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApi
{


    //https://farm-bazar-api.herokuapp.com/api/order/add/61bc633a767e441b8c63c225

    // TODO
//    @GET("api/store/get/{id}")
//    suspend fun getStoreById(@Path("id")id:String): Response<StoreDetailsResponse>
//    // Response store Id , StoreName, store Desc , Seller Id, seller Name , seller Image

    // TODO
     @POST("api/order/add/{id}")
    suspend fun placeOrder(@Path("id")productId:String, @Body orderRequest: OrderRequest):Response<OrderResponse>

}