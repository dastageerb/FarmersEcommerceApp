package com.example.farmersecom.features.productStore.domain.model.storeProducts


import com.example.farmersecom.features.productStore.domain.model.storeProducts.Product
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StoreProducts(
    @Json(name = "products")
    var products: List<Product>?
)