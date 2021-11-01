package com.example.farmersecom.features.search.presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentSearchBinding


class SearchFragment : BaseFragment<FragmentSearchBinding>()
{

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentSearchBinding
    {
        return FragmentSearchBinding.inflate(inflater,container,false);
    }

}