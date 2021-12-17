package com.example.farmersecom.features.home.di

import com.example.farmersecom.features.authentication.di.NonAuthRetrofit
import com.example.farmersecom.features.home.data.business.HomeRepositoryImpl
import com.example.farmersecom.features.home.data.framework.HomeApi
import com.example.farmersecom.features.home.domain.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule
{


    @Provides
    @Singleton
    fun providesHomeApi(@NonAuthRetrofit retrofit: Retrofit):HomeApi = retrofit.create(HomeApi::class.java)

    @Provides
    @Singleton
    fun providesHomeRepository(homeApi: HomeApi):HomeRepository = HomeRepositoryImpl(homeApi)


}