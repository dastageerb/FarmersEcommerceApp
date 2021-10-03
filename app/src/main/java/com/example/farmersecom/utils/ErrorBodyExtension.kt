package com.example.farmersecom.utils

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

object ErrorBodyExtension
{

    fun ResponseBody.getMessage():String
    {
        return try
        {
            val jsonObj = JSONObject(this.charStream().readText())
            jsonObj.getString("message")
        }catch (e:Exception)
        {
            e.message.toString()
        }
    }


}