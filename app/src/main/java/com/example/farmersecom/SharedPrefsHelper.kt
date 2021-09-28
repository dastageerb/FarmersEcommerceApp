package com.example.farmersecom

import android.content.SharedPreferences
import com.example.farmersecom.utils.Constants.TOKEN
import javax.inject.Inject

class SharedPrefsHelper @Inject constructor(private val prefs: SharedPreferences,
                                            private val editor: SharedPreferences.Editor)
{


    fun savePrefs(data: String?)
    {
        editor.putString(TOKEN, data)
        editor.commit()
    } //


    fun getPrefs(): String?
    {
        return prefs.getString(TOKEN, null)
    }


} // DataStoreRepo