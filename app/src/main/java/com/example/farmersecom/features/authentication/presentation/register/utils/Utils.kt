package com.example.farmersecom.features.authentication.presentation.register.utils

import android.content.Context
import android.content.res.Resources
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.farmersecom.R

object Utils
{


    fun Int.getList(resources: Resources): Array<String> = resources.getStringArray(this)


    fun AutoCompleteTextView.setUpAdapter(context:Context,id:Int)
    {

        val array = resources.getStringArray(id)
        val arrayAdapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item
            ,array)
        this.setAdapter(arrayAdapter)

    } // setAdapter closed



} // Adapter setup closed