package com.example.farmersecom.features.buyerSection.presentation.orderDetailsForBuyer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentOrderDetailsForBuyerBinding


class OrderDetailsForBuyerFragment : BaseFragment<FragmentOrderDetailsForBuyerBinding>()
{
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentOrderDetailsForBuyerBinding
    {
        return FragmentOrderDetailsForBuyerBinding.inflate(inflater,container,false)
    }

}