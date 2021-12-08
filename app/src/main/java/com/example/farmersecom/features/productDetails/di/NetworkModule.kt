package com.example.farmersecom.features.productDetails.di

import com.example.farmersecom.di.AuthRetrofit
import com.example.farmersecom.features.authentication.di.NonAuthRetrofit
import com.example.farmersecom.features.productDetails.data.business.ProductDetailsImpl
import com.example.farmersecom.features.productDetails.data.frameWork.ProductDetailsApi
import com.example.farmersecom.features.productDetails.domain.ProductDetailsRepository
import com.example.farmersecom.features.profile.data.business.ProfileRepoImpl
import com.example.farmersecom.features.profile.data.framework.ChangePhotoNetworkEntityMapper
import com.example.farmersecom.features.profile.data.framework.ProfileNetworkEntityMapper
import com.example.farmersecom.features.profile.data.framework.ProfileApi
import com.example.farmersecom.features.profile.domain.ProfileRepository
import com.example.farmersecom.features.storeAdmin.data.business.StoreAdminImpl
import com.example.farmersecom.features.storeAdmin.data.framework.StoreAdminApi
import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
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
    fun providesProductDetails(@NonAuthRetrofit retrofit: Retrofit): ProductDetailsApi =
        retrofit.create(ProductDetailsApi::class.java)


//
    @Provides
    @Singleton
    fun provideProductDetailsRepository(productDetailsApi: ProductDetailsApi):ProductDetailsRepository =
        ProductDetailsImpl(productDetailsApi)



}