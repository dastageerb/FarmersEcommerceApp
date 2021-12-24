package com.example.farmersecom.features.storeAdmin.domain.model.changeStoreImage

import com.squareup.moshi.Json

data class ChangeStoreImageResponse(
    @Json(name = "message")
    val message: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "storeImgUrl")
    val storeImgUrl: String
)