package com.example.farmersecom.base

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.farmersecom.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication : Application()
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