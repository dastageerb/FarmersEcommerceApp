package com.example.farmersecom.features.storeAdmin.presentation.orderDetailsForSeller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentOrderDetailsForSellerBinding


class OrderDetailsForSellerFragment : BaseFragment<FragmentOrderDetailsForSellerBinding>()
{

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentOrderDetailsForSellerBinding
    {
        return FragmentOrderDetailsForSellerBinding.inflate(inflater,container,false)
    } // createView closed


} // OrderDetailsForSellerFragment closed