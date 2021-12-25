package com.example.farmersecom.di

import android.content.SharedPreferences
import com.example.farmersecom.BuildConfig
import com.example.farmersecom.SharedPrefsHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import okhttp3.Interceptor
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
    fun providesIntercept(sharedPreferences:SharedPrefsHelper) :Interceptor
    {

        val token = sharedPreferences.getToken()
        return Interceptor{ chain ->
            val request = chain.request()
            val newRequest = request.newBuilder().addHeader("Authorization", "Bearer $token")
            chain.proceed(newRequest.build())
        }
    }

    @AuthHttpClient
    @Provides
    @Singleton
    fun providesOkHttpClient(interceptor:Interceptor):OkHttpClient
    {

            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

                return OkHttpClient.Builder()
                     .addInterceptor(loggingInterceptor)
                .addInterceptor(interceptor)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1,TimeUnit.MINUTES)
                .build()
    } // provideOkHttpClient

   @AuthRetrofit
   @Provides
   @Singleton
   fun providesRetrofit(@AuthHttpClient client:OkHttpClient):Retrofit = Retrofit.Builder()
       .client(client)
       .baseUrl(BuildConfig.BASE_URL)
       .addConverterFactory(MoshiConverterFactory.create())
       .build()



} //