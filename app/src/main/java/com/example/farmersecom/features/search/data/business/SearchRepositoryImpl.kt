package com.example.farmersecom.features.search.data.business
import com.example.farmersecom.features.search.data.frameWork.SearchApi
import com.example.farmersecom.features.search.domain.SearchRepository
import com.example.farmersecom.features.search.domain.model.categories.CategoriesResponse
import retrofit2.Response

class SearchRepositoryImpl(private val searchApi: SearchApi) : SearchRepository
{

    override suspend fun getAllCategories(): Response<CategoriesResponse>
            = searchApi.getAllCategories()


    override suspend fun searchItem(query: String, category: String?, location: String?)
    = searchApi.getSearchedResult(query,category,location)

//= Pager(PagingConfig(20)) { SearchPagingSource(searchApi,query,category,location) }.flow




}