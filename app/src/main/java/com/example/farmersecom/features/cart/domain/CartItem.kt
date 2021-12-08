package com.example.farmersecom.features.cart.domain

data class CartItem(
    val productName:String,
    val productQuantity:String,
    val productPrice: String,
    val productUnit:String,
    val productImageUrl:String)