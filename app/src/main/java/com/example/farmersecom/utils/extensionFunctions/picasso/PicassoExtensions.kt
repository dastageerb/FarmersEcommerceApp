package com.example.farmersecom.utils.extensionFunctions.picasso

import android.widget.ImageView
import com.squareup.picasso.Picasso

object PicassoExtensions
{


    fun ImageView.load(url:String?)
    {
        if(url!!.isNotEmpty())
        {
            Picasso.get().load(url).into(this)
        }

    }


    fun ImageView.load(url: String?, placeHolder: Int)
    {
        Picasso.get().load(url).placeholder(placeHolder).into(this)
    }


}