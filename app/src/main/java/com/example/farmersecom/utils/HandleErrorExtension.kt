package com.example.farmersecom.utils

import com.example.akhbar.utils.NetworkResource
import retrofit2.HttpException
import java.lang.Exception

object HandleErrorExtension
{

    fun Exception.handleException():String
    {
      return when(this)
        {
            is HttpException -> "Http Exception:${this.message}"
            else ->  "No Internet Connection: "
        } // when closed
    } //



}