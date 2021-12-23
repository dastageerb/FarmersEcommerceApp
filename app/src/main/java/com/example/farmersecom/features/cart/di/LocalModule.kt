package com.example.farmersecom.features.cart.di

import com.example.farmersecom.DatabaseHelper
import com.example.farmersecom.features.cart.data.business.CartRepositoryImpl
import com.example.farmersecom.features.cart.data.framework.CartDao
import com.example.farmersecom.features.cart.domain.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule
{


    @Provides
    @Singleton
    fun provideCartRepository(cartDao: CartDao)
            : CartRepository = CartRepositoryImpl(cartDao)


    @Provides
    @Singleton
    fun providesCartDao(databaseHelper: DatabaseHelper): CartDao =
            databaseHelper.cartDao()



}