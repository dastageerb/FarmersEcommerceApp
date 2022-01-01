package com.example.farmersecom.features.communitySection.di

import com.example.farmersecom.di.AuthRetrofit
import com.example.farmersecom.features.communitySection.data.business.CommunityRepositoryImpl
import com.example.farmersecom.features.communitySection.data.framework.CommunityApi
import com.example.farmersecom.features.communitySection.domain.CommunityRepository
import com.example.farmersecom.features.home.data.framework.HomeApi
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


    @Singleton
    @Provides
    fun providesCommunityApi(@AuthRetrofit retrofit: Retrofit)
            :CommunityApi = retrofit.create(CommunityApi::class.java)


    @Provides
    @Singleton
    fun providesCommunityRepository(communityApi: CommunityApi,homeApi: HomeApi):CommunityRepository
     = CommunityRepositoryImpl(communityApi,homeApi)


} // NetworkModule