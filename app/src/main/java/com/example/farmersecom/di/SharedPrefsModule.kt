package com.example.farmersecom.di

import android.content.Context
import android.content.SharedPreferences
import com.example.farmersecom.utils.constants.Constants.SAVED_PREFS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefsModule
{


    @Singleton
    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences
    {
        return  context.getSharedPreferences(SAVED_PREFS, Context.MODE_PRIVATE);
    }


    @Singleton
    @Provides
    fun provideSharedPrefsEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor
    {
        return  sharedPreferences.edit();
    }







}