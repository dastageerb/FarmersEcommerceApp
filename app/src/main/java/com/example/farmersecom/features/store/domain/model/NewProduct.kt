package com.example.farmersecom.features.store.domain.model

data class NewProduct(
    val productName:String,
    val productDescription:String,
    val productQuantityUnit:String,
    val productQuantity:String,
    val productPrice:String,
    val productImages:List<String>

    )