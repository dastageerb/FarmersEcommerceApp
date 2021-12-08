package com.example.farmersecom.features.search.domain

import androidx.paging.PagingData
import com.example.farmersecom.features.search.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow

interface SearchRepository
{
    suspend fun searchItem(query:String, category:String, location:String): Flow<PagingData<SearchItem>>


}