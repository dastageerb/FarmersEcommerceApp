package com.example.farmersecom.features.productStore.di

import com.example.farmersecom.features.authentication.di.NonAuthRetrofit
import com.example.farmersecom.features.productStore.data.business.ProductStoreRepositoryImpl
import com.example.farmersecom.features.productStore.data.frameWork.ProductStoreApi
import com.example.farmersecom.features.productStore.domain.ProductStoreRepository
import com.example.farmersecom.features.search.data.business.SearchRepositoryImpl
import com.example.farmersecom.features.search.data.frameWork.SearchApi
import com.example.farmersecom.features.search.domain.SearchRepository
import com.google.gson.JsonObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Query
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule
{



    @Provides
    @Singleton
    fun providesProductStoreApi(@NonAuthRetrofit retrofit: Retrofit): ProductStoreApi = retrofit.create(ProductStoreApi::class.java)


    @Provides
    @Singleton
    fun provideSearchRepository(productStoreApi: ProductStoreApi)
            : ProductStoreRepository = ProductStoreRepositoryImpl(productStoreApi)


} // networkModule closed