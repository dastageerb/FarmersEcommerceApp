package com.example.farmersecom.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object Permissions
{


    fun hasPermission(context: Context):Boolean
    {
        return  (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }


} /// Permissions closed