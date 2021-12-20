package com.example.farmersecom.features.buyNow.di

import com.example.farmersecom.di.AuthRetrofit
import com.example.farmersecom.features.buyNow.data.business.PlaceOrderRepositoryImpl
import com.example.farmersecom.features.buyNow.data.framwork.OrderApi
import com.example.farmersecom.features.buyNow.domain.PlaceOrderRepository
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
    fun provideOrderApi(@AuthRetrofit retrofit: Retrofit) :OrderApi = retrofit.create(OrderApi::class.java)

    @Provides
    @Singleton
    fun provideOrderRepository(orderApi: OrderApi) : PlaceOrderRepository  = PlaceOrderRepositoryImpl(orderApi)



} // NetworkModule closed