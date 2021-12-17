package com.example.farmersecom.features.home.domain

import com.example.farmersecom.features.home.domain.model.HomeLatestItem
import com.example.farmersecom.features.home.domain.model.HomeSlider
import retrofit2.Response

interface HomeRepository
{


    suspend fun getSliderItems():Response<List<HomeSlider>>

    suspend fun getHomeLatestItems():Response<List<HomeLatestItem>>



}