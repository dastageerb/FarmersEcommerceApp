package com.example.farmersecom.utils.extensionFunctions.handleErros

import okhttp3.ResponseBody
import org.json.JSONObject

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