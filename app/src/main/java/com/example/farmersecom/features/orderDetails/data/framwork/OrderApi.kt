package com.example.farmersecom.features.orderDetails.data.framwork

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET

interface OrderApi
{


    // TODO
     //@("") PUT/POST
    suspend fun placeOrder( ):Response<JsonObject>

}