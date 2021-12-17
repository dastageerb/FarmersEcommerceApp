package com.example.farmersecom.features.orderDetails.di

import com.example.farmersecom.di.AuthRetrofit
import com.example.farmersecom.features.orderDetails.data.business.PlaceOrderRepositoryImpl
import com.example.farmersecom.features.orderDetails.data.framwork.OrderApi
import com.example.farmersecom.features.orderDetails.domain.PlaceOrderRepository
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