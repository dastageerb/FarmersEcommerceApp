package com.example.farmersecom.features.home.data.framework

import com.example.farmersecom.features.home.domain.model.HomeLatestItem
import com.example.farmersecom.features.home.domain.model.HomeSlider
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi
{


    // TODO
    @GET("")
    suspend fun getSliderItems():Response<List<HomeSlider>>

    @GET("")
    suspend fun getLatestProducts():Response<List<HomeLatestItem>>


}