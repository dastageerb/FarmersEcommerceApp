package com.example.farmersecom.features.storeAdmin.data.framework.entities.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import okhttp3.MultipartBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.http.Multipart

data class NewProduct(
    val productName: String,
    val productPrice: Int,
    val productDescription: String,
    val productCategory: String,
    val productUnit: String,
    val productLocation:String,
    val productQuantity: Int)
