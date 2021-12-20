package com.example.farmersecom.features.search.domain

import androidx.paging.PagingData
import com.example.farmersecom.features.search.domain.model.SearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SearchRepository
{
    suspend fun searchItem(query:String, category:String, location:String):Response<SearchResponse>


}