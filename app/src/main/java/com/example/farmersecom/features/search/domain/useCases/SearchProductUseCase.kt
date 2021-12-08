package com.example.farmersecom.features.search.domain.useCases

import com.example.farmersecom.features.search.domain.SearchRepository
import javax.inject.Inject

class SearchProductUseCase  @Inject constructor(private val SearchRepository: SearchRepository)
{
    suspend fun searchItem(query:String,category:String,location:String) = SearchRepository.searchItem(query,category,location)

}