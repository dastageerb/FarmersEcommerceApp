package com.example.farmersecom.features.home.domain.usecases

import com.example.farmersecom.features.home.domain.HomeRepository
import javax.inject.Inject

class GetMoreCategoryItemsUseCase @Inject constructor(private val homeRepository: HomeRepository)
{
    suspend fun getMoreCategoryItems(categoryId:String) = homeRepository.moreCategoryItems(categoryId)
}