package com.example.farmersecom.authentication.di

import com.example.farmersecom.BuildConfig
import com.example.farmersecom.authentication.data.AuthApi
import com.example.farmersecom.authentication.data.AuthRepositoryImpl
import com.example.farmersecom.authentication.domain.repository.AuthRepository
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
    fun providesOkHttpClient():OkHttpClient
    {

        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
      return  OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    } // provideOkHttpClient

   @Provides
   @Singleton
   fun providesRetrofit(client:OkHttpClient):Retrofit = Retrofit.Builder()
       .client(client)
       .baseUrl(BuildConfig.BASE_URL)
       .addConverterFactory(MoshiConverterFactory.create())
       .build()

   @Provides
   @Singleton
   fun providesAuthApi(retrofit: Retrofit):AuthApi = retrofit.create(AuthApi::class.java)

   @Provides
   @Singleton
   fun provideRepository(authApi: AuthApi):AuthRepository = AuthRepositoryImpl(authApi)


} //