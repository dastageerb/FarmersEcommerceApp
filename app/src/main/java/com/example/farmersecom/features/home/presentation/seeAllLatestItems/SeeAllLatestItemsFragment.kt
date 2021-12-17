package com.example.farmersecom.features.home.presentation.seeAllLatestItems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentSeeAllLatestItemsBinding
import com.example.farmersecom.features.home.presentation.SharedViewModel
import com.example.farmersecom.utils.constants.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class SeeAllLatestItemsFragment : BaseFragment<FragmentSeeAllLatestItemsBinding>()
{

    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentSeeAllLatestItemsBinding
    {
        return FragmentSeeAllLatestItemsBinding.inflate(inflater,container,false)
    } // createView closed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            sharedViewModel.getCategoryItem.collect()
            {
                Timber.tag(Constants.TAG).d("${it.categoryName}")
            } //
        } // viewLifeCycleOwner

    } // onViewCreated closed

} // SeeAllLatestItemsFragment