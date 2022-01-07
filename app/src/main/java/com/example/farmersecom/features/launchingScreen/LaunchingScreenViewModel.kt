package com.example.farmersecom.features.launchingScreen

import androidx.lifecycle.ViewModel
import com.example.farmersecom.SharedPrefsHelper
import com.example.farmersecom.utils.constants.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchingScreenViewModel @Inject constructor(val sharedPrefsHelper: SharedPrefsHelper):ViewModel()
{


    fun setLanguage(language: String?) = sharedPrefsHelper.setLanguage(language)
    fun getLanguage(): String? = sharedPrefsHelper.getLanguage()
    fun isFirstLaunch():Boolean = sharedPrefsHelper.isFirstLaunch()


    fun changeFirstLaunch(boolean: Boolean) = sharedPrefsHelper.changeFirstLaunch(boolean)





}