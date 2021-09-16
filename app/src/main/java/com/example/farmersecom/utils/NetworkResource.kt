package com.example.akhbar.utils

sealed class NetworkResource<T>(private val data:T?=null,private val msg:String?=null)
{

    class Success<T>(val data:T?) : NetworkResource<T>(data)
    class Error<T>(val msg: String): NetworkResource<T>(null,msg)
    class Loading<T>: NetworkResource<T>()
    class None<T>: NetworkResource<T>()

}