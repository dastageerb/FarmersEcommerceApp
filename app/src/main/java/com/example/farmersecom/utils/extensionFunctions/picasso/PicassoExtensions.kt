package com.example.farmersecom.utils.extensionFunctions.picasso

import android.widget.ImageView
import com.example.farmersecom.R
import com.squareup.picasso.Picasso

object PicassoExtensions
{


    fun ImageView.load(url:String?)
    {
        if(url!!.isNotEmpty())
        {
            Picasso.get().load(url).placeholder(R.drawable.image_place_holder).into(this)
        }

    }


    fun ImageView.load(url: String?, placeHolder: Int)
    {
        if(url!!.isNotEmpty())
        {
            Picasso.get().load(url).placeholder(placeHolder).into(this)
        }
    }


}