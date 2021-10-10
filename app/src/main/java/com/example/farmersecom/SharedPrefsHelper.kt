package com.example.farmersecom

import android.content.SharedPreferences
import com.example.farmersecom.utils.constants.Constants.TOKEN
import javax.inject.Inject

class SharedPrefsHelper @Inject constructor(private val prefs: SharedPreferences,
                                            private val editor: SharedPreferences.Editor)
{


    fun saveToken(data: String?)
    {
        editor.putString(TOKEN, data)
        editor.commit()
    }

    fun clearToken()
    {
        editor.remove(TOKEN)
        editor.commit()
    }


    fun getToken(): String?
    {
        return prefs.getString(TOKEN, null)
    }


} // DataStoreRepo