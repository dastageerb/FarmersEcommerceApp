package com.example.farmersecom.features.search.domain

import com.example.farmersecom.features.search.domain.model.SearchResponse
import com.example.farmersecom.features.search.domain.model.categories.CategoriesResponse
import retrofit2.Response

interface SearchRepository
{
    suspend fun searchItem(query:String, category:String?, location:String?):Response<SearchResponse>

    suspend fun getAllCategories():Response<CategoriesResponse>



}