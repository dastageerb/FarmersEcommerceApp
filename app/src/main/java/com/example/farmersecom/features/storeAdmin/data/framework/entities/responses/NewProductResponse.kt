package com.example.farmersecom.features.storeAdmin.data.framework.entities.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.Response

@JsonClass(generateAdapter = true)
data class NewProductResponse(
    @Json(name = "message")
    val message: String?,
    @Json(name = "status")
    val status: String?
)