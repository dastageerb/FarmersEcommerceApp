package com.example.farmersecom.features.profile.di

import com.example.farmersecom.di.AuthRetrofit
import com.example.farmersecom.features.authentication.data.AuthApi
import com.example.farmersecom.features.authentication.data.AuthRepositoryImpl
import com.example.farmersecom.features.authentication.domain.repository.AuthRepository
import com.example.farmersecom.features.profile.data.business.ProfileRepoImpl
import com.example.farmersecom.features.profile.data.framework.ProfileApi
import com.example.farmersecom.features.profile.domain.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
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
    fun provideProfileRepository(profileApi: ProfileApi): ProfileRepository = ProfileRepoImpl(profileApi)


}