package com.example.farmersecom.features.home.data.business

import com.example.farmersecom.features.home.data.framework.HomeApi
import com.example.farmersecom.features.home.domain.HomeRepository
import com.example.farmersecom.features.home.domain.model.HomeLatestItem
import com.example.farmersecom.features.home.domain.model.HomeSlider
import retrofit2.Response

class HomeRepositoryImpl(private val homeApi: HomeApi):HomeRepository
{
    override suspend fun getSliderItems(): Response<List<HomeSlider>>
    =homeApi.getSliderItems()

    override suspend fun getHomeLatestItems(): Response<List<HomeLatestItem>>
    = homeApi.getLatestProducts()

}