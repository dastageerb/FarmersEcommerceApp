package com.example.farmersecom.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import java.util.*

abstract class BaseFragment<VB:ViewBinding> : Fragment()
{

    private var _binding:VB?=null
    val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = createView(inflater,container,false)

     return binding.root
    } // onCreateView closed


    abstract fun createView(inflater: LayoutInflater, container: ViewGroup?, root:Boolean):VB


    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    } // onDestroy closed









} // BaseFragment closed