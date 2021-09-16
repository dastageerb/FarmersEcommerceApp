package com.example.akhbar.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.flow.MutableStateFlow

object MonitorNetwork : ConnectivityManager.NetworkCallback()
{

    private val  isNetworkAvailAble:MutableStateFlow<Boolean> = MutableStateFlow(false)


    fun checkNetworkAvailAbility(context: Context) : MutableStateFlow<Boolean>
    {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            connectivityManager.registerDefaultNetworkCallback(this)
        }else
        {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), this)
        }

        var isConnected = false

        connectivityManager.allNetworks.forEach()
        {
            val networkCapability = connectivityManager.getNetworkCapabilities(it)

            networkCapability?.let()
            {
                if(it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
                {
                    isConnected = true
                    return@forEach
                } // if closed
            } // let closed
        } // for Each closed

        isNetworkAvailAble.value = isConnected
        return isNetworkAvailAble

    } // checkNetworkAvailAbility closed



    override fun onAvailable(network: Network)
    {
        super.onAvailable(network)
        isNetworkAvailAble.value = true


    } // onAvailAbles closed

    override fun onLost(network: Network)
    {
        super.onLost(network)
        isNetworkAvailAble.value = false

    } // onLost closed
} // MonitorNetwork closed