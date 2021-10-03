package com.example.akhbar.utils

import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


object ViewExtension
{


    fun View.show()
    {
        this.visibility = View.VISIBLE
    }

    fun View.hide()
    {
        this.visibility = View.GONE
    }

    fun ImageView.load(url:String?)
    {
        Picasso.get().load(url).into(this)
    }


    fun ImageView.load(url: String?, placeHolder: Int)
    {
        Picasso.get().load(url).placeholder(placeHolder).into(this)
    }





}