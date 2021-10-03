package com.example.farmersecom.features.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentStoreBinding

class StoreFragment : BaseFragment<FragmentStoreBinding>()
{

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentStoreBinding
    {

       return FragmentStoreBinding.inflate(inflater,container,false)
    } // onCreateView closed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
    }

} // StoreFragment