package com.example.farmersecom.features.search.data.business

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.farmersecom.features.search.data.frameWork.SearchApi
import com.example.farmersecom.features.search.domain.SearchRepository

class SearchRepositoryImpl(private val searchApi: SearchApi) : SearchRepository
{

    override suspend fun searchItem(query: String, category: String, location: String)
    = searchApi.getSearchedResult(query,category,location)

//= Pager(PagingConfig(20)) { SearchPagingSource(searchApi,query,category,location) }.flow





}