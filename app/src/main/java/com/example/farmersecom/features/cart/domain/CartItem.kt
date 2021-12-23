package com.example.farmersecom.features.cart.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.farmersecom.utils.constants.Constants
import java.time.OffsetDateTime
import java.util.*

@Entity(tableName = Constants.CART_TABLE)
data class CartItem(
    @PrimaryKey(autoGenerate = false)
    val productId:String,
    val productName:String,
    val productQuantity:Int,
    val productPrice: Int,
    val productQuantityUnit:String,
    val productImageUrl:String,
    val deliveryCharges:Int,
    val date:Date?=null
    )