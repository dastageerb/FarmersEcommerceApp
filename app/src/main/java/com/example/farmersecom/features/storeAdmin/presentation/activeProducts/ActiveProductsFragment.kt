package com.example.farmersecom.features.storeAdmin.presentation.activeProducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentActiveProductsBinding
import com.example.farmersecom.features.storeAdmin.presentation.StoreDashboardViewModel
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
@InternalCoroutinesApi
class ActiveProductsFragment : BaseFragment<FragmentActiveProductsBinding>()
{

    private val  viewModel:StoreDashboardViewModel by viewModels()

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentActiveProductsBinding
    {
        return FragmentActiveProductsBinding.inflate(inflater,container,false)
    } // onCreateView closed



    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

      //  viewModel.getProductByStatus(true)
     //   subscribeToActiveProductsResponse()

    } // onViewCreated closed



    private fun subscribeToActiveProductsResponse()
    {

//        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
//        {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
//            {
//                viewModel.productByStatusResponse.collect()
//                {
//                    when(it)
//                    {
//                        is NetworkResource.Success ->
//                        {
//                            Timber.tag(Constants.TAG).d("${it.data}")
//                            // updateViews(it.data)
//                        }
//                        is NetworkResource.Error ->
//                        {
//                            Timber.tag(Constants.TAG).d("${it.msg}")
//                        }
//                    }// when closed
//                } // getProfile closed
//            } // repeatOnLife cycle closed
//        } /// lifecycleScope closed
    } // subscribeToActiveProductsResponse



} // activeProductsFragment closed