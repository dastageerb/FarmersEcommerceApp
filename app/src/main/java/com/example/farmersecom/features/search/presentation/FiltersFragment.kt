package com.example.farmersecom.features.search.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentFiltersBinding


class FiltersFragment : BaseFragment<FragmentFiltersBinding>()
{
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentFiltersBinding
    {
        return FragmentFiltersBinding.inflate(inflater,container,false);

    } // createView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

    } // onViewCreated closed


} // onFilters closed