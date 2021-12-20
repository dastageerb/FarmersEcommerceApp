package com.example.farmersecom.features.search.domain.model


import com.example.farmersecom.features.search.domain.model.Product
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    @Json(name = "products")
    var products: List<Product>?
)