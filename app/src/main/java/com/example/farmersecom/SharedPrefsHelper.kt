package com.example.farmersecom

import android.content.SharedPreferences
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.User
import com.example.farmersecom.features.productDetails.domain.model.NavigationEntity
import com.example.farmersecom.utils.constants.Constants.CATEGORY
import com.example.farmersecom.utils.constants.Constants.LOCATION
import com.example.farmersecom.utils.constants.Constants.NAVIGATION
import com.example.farmersecom.utils.constants.Constants.TOKEN
import com.example.farmersecom.utils.constants.Constants.USER
import com.google.gson.Gson
import javax.inject.Inject

class SharedPrefsHelper @Inject constructor(private val prefs: SharedPreferences,
                                            private val editor: SharedPreferences.Editor)
{


     /** Toke CRUDS **/
    fun saveToken(data: String?)
    {
        editor.putString(TOKEN, data)
        editor.commit()
    }

    fun clearToken()
    {

        editor.putString(TOKEN,"")
        editor.commit()
    }


    fun getToken(): String?
    {
        return prefs.getString(TOKEN,"")
    }


    /** USER CRUDS ***/

    fun saveUser(data: User?)
    {
        val user = Gson().toJson(data)
        editor.putString(USER, user)
        editor.commit()
    }

    fun clearUser()
    {
        editor.putString(USER,"")
        editor.commit()
    }


    fun getUser(): User?
    {
        val data = prefs.getString(USER,"")
        return Gson().fromJson(data,User::class.java)
    }


    /**Search Filter CRUDS **/


    fun saveLocation(data: String?)
    {
        editor.putString(LOCATION, data)
        editor.commit()
    }



    fun getLocation(): String?
    {
        return prefs.getString(LOCATION,null)
    }


    fun saveCategory(data: String?)
    {
        editor.putString(CATEGORY, data)
        editor.commit()
    }


    fun getCategory(): String?
    {
        return prefs.getString(CATEGORY,null)
    }

    fun clearFilters()
    {
        editor.putString(LOCATION,null)
        editor.putString(CATEGORY,null)
        editor.commit()
    }


} // DataStoreRepo