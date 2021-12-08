package com.example.farmersecom.features.search.di

import com.example.farmersecom.features.authentication.di.NonAuthRetrofit
import com.example.farmersecom.features.search.data.business.SearchRepositoryImpl
import com.example.farmersecom.features.search.data.frameWork.SearchApi
import com.example.farmersecom.features.search.domain.SearchRepository
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
    fun providesSearchApi(@NonAuthRetrofit retrofit: Retrofit): SearchApi = retrofit.create(SearchApi::class.java)


    @Provides
    @Singleton
    fun provideSearchRepository(searchApi: SearchApi)
            : SearchRepository = SearchRepositoryImpl(searchApi)



} // NetworkModule closed