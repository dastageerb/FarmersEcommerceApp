package com.example.farmersecom.features.home.presentation.moreSliderItems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentMoreSliderItemsBinding
import com.example.farmersecom.features.home.presentation.SharedViewModel
import com.example.farmersecom.utils.constants.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MoreSliderItemsFragment : BaseFragment<FragmentMoreSliderItemsBinding>()
{

    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentMoreSliderItemsBinding
    {
        return FragmentMoreSliderItemsBinding.inflate(inflater,container,false)
    } // createView closed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            sharedViewModel.getSliderQuery.collect()
            {
                Timber.tag(TAG).d("$it")
            } //
        } // viewLifeCycleOwner
    } // onViewCreated closed



} // MoreSliderItemsFragment closed