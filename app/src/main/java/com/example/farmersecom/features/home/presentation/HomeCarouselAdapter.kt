package com.example.farmersecom.features.home.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.farmersecom.features.home.domain.HomeCarousel

class HomeCarouselAdapter(val context:Context,val carousel:List<HomeCarousel>) : PagerAdapter()
{
    override fun getCount(): Int
    {
        TODO("Not yet implemented")
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean
    {
        TODO("Not yet implemented")
    }



} // HomeCarouselAdapter