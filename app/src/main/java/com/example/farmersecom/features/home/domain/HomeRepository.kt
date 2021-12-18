package com.example.farmersecom.features.home.domain

import androidx.paging.PagingData
import com.example.farmersecom.features.home.domain.model.MoreProductsResponseItem
import com.example.farmersecom.features.home.domain.model.homeModels.HomeLatestItem
import com.example.farmersecom.features.home.domain.model.more.MoreProduct
import com.example.farmersecom.features.home.domain.model.more.MoreResponseItem
import com.example.farmersecom.features.home.domain.model.sliderModels.HomeSlider
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface HomeRepository
{


    suspend fun getSliderItems():Response<List<HomeSlider>>

    suspend fun getHomeLatestItems():Response<List<HomeLatestItem>>
//
//    suspend fun moreSliderItems(query:String): Flow<PagingData<MoreProduct>>

    suspend fun moreSliderItems(query:String): Response<MoreResponseItem>

    suspend fun moreCategoryItems(categoryId:String): Response<MoreResponseItem>


}