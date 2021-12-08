package com.example.farmersecom.features.buyerSection.di

import com.example.farmersecom.di.AuthRetrofit
import com.example.farmersecom.features.authentication.di.NonAuthRetrofit
import com.example.farmersecom.features.buyerSection.data.business.BuyerDashBoardRepositoryImpl
import com.example.farmersecom.features.buyerSection.data.framework.BuyerDashboardApi
import com.example.farmersecom.features.buyerSection.domain.BuyerDashboardRepository
import com.example.farmersecom.features.productStore.data.business.ProductStoreRepositoryImpl
import com.example.farmersecom.features.productStore.data.frameWork.ProductStoreApi
import com.example.farmersecom.features.productStore.domain.ProductStoreRepository
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
    fun providesBuyerDashboardApi(@AuthRetrofit retrofit: Retrofit): BuyerDashboardApi = retrofit.create(BuyerDashboardApi::class.java)


    @Provides
    @Singleton
    fun provideBuyerDashboardRepository(buyerDashboardApi: BuyerDashboardApi)
            : BuyerDashboardRepository = BuyerDashBoardRepositoryImpl(buyerDashboardApi)




}