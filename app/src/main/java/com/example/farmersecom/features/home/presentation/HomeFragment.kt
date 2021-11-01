package com.example.farmersecom.features.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentHomeBinding

class HomeFragment :BaseFragment<FragmentHomeBinding>()
{


    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentHomeBinding
    {
        return  FragmentHomeBinding.inflate(inflater,container,false)
    } // onCreateView closed


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


} // HomeFragment closed