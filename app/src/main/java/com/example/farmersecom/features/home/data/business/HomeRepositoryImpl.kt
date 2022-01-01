package com.example.farmersecom.features.home.data.business

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.farmersecom.features.home.data.framework.HomeApi
import com.example.farmersecom.features.home.data.framework.MoreSliderItemsPagingSource
import com.example.farmersecom.features.home.domain.HomeRepository
import com.example.farmersecom.features.home.domain.model.homeModels.HomeLatestItem
import com.example.farmersecom.features.home.domain.model.more.MoreResponseItem
import com.example.farmersecom.features.home.domain.model.sliderModels.HomeSlider
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class HomeRepositoryImpl(private val homeApi: HomeApi):HomeRepository
{
    override suspend fun getSliderItems(): Response<List<HomeSlider>>
    =homeApi.getSliderItems()

    override suspend fun getHomeLatestItems(): Response<List<HomeLatestItem>>
    = homeApi.getLatestProducts()

    override suspend fun moreSliderItems(query:String)
    = homeApi.getMoreSliderItems(query,1,200)

    override suspend fun moreCategoryItems(categoryId: String): Response<MoreResponseItem>
     =   homeApi.getMoreCategoryItems(categoryId,1,200)



}