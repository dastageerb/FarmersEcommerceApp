package com.example.farmersecom.features.productStore.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentProductStoreBinding


class ProductStoreFragment : BaseFragment<FragmentProductStoreBinding>()
{

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentProductStoreBinding
    {
        return FragmentProductStoreBinding.inflate(inflater,container,false);
    } //

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
    } // onViewCreated



} // productStoreFragment