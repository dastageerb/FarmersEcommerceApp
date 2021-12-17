package com.example.farmersecom.features.cart.di

import com.example.farmersecom.di.AuthRetrofit
import com.example.farmersecom.features.authentication.di.NonAuthRetrofit
import com.example.farmersecom.features.cart.data.business.CartRepositoryImpl
import com.example.farmersecom.features.cart.data.framework.CartApi
import com.example.farmersecom.features.cart.domain.CartRepository
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
    fun providesCartApi(@AuthRetrofit retrofit: Retrofit): CartApi = retrofit.create(CartApi::class.java)


    @Provides
    @Singleton
    fun provideCartRepository(cartApi: CartApi)
            : CartRepository = CartRepositoryImpl(cartApi)





}