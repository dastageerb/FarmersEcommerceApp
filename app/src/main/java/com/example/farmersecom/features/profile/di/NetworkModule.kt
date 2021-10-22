package com.example.farmersecom.features.profile.di

import com.example.farmersecom.di.AuthRetrofit
import com.example.farmersecom.features.profile.data.business.ProfileRepoImpl
import com.example.farmersecom.features.profile.data.framework.ChangePhotoNetworkEntityMapper
import com.example.farmersecom.features.profile.data.framework.ProfileNetworkEntityMapper
import com.example.farmersecom.features.profile.data.framework.ProfileApi
import com.example.farmersecom.features.profile.domain.ProfileRepository
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
    fun providesProfileApi(@AuthRetrofit retrofit: Retrofit): ProfileApi = retrofit.create(ProfileApi::class.java)


    @Provides
    @Singleton
    fun provideProfileRepository(profileApi: ProfileApi,
                                 profileNetworkEntityMapper: ProfileNetworkEntityMapper,
                                 changePhotoMapper: ChangePhotoNetworkEntityMapper)
    : ProfileRepository = ProfileRepoImpl(profileApi,profileNetworkEntityMapper,changePhotoMapper)



}