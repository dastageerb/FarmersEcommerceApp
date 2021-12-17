package com.example.farmersecom.features.productStore.domain.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StoreProductsResponse(
    @Json(name = "products")
    val products: List<Product>
)