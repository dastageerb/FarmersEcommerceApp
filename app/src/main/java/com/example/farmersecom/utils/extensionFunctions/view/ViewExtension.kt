package com.example.farmersecom.utils.extensionFunctions.view

import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


object ViewExtension
{


    fun View.show()
    {
        if(this.visibility == View.GONE)
        {
            this.visibility = View.VISIBLE
        }
    }

    fun View.hide()
    {
        if(this.visibility == View.VISIBLE)
        {
            this.visibility = View.GONE
        }
    }






}