package com.example.farmersecom.features.cart.domain

import com.squareup.moshi.Json

class CheckOutItem(    var address: String?,
                       var city: String?,
                       var name: String?,
                       var paymentOption: String?,
                       var postalCode: Int?,
                       var contactNumber:String)
