package com.example.farmersecom.base


import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.example.farmersecom.BuildConfig
import com.ninenox.kotlinlocalemanager.ApplicationLocale
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication : ApplicationLocale()
{

    override fun onCreate()
    {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        if (BuildConfig.DEBUG)
        {
            Timber.plant(Timber.DebugTree())
        }

    }




}