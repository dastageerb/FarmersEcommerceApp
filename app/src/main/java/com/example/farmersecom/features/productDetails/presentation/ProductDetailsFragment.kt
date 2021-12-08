package com.example.farmersecom.features.productDetails.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentProductDetailsBinding
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>()
{
    val id = "61add66551c87d29dc08d41c"

    private val viewModel:ProductDetailsViewModel by viewModels()

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentProductDetailsBinding
    {
        return FragmentProductDetailsBinding.inflate(inflater,container,false);
    } // onCreateView closed




    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProductDetails(id)
        subscribeProductDetailsResponseFlow()
    } // onViewCreated



    private fun subscribeProductDetailsResponseFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.productDetailsResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Success ->
                        {
                            Timber.tag(Constants.TAG).d("${it.data}")
                            updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscribeProfileResponseFlow closed


    private fun updateViews(data: JsonObject?)
    {

    } //

} // ProductDetailsFragment