package com.example.farmersecom.features.productDetails.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentProductDetailsBinding


class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>()
{

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentProductDetailsBinding
    {
        return FragmentProductDetailsBinding.inflate(inflater,container,false);
    } // onCreateView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
    }


} // ProductDetailsFragment