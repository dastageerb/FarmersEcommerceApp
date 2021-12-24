package com.example.farmersecom.features.storeAdmin.presentation.activeProducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentActiveProductsBinding
import com.example.farmersecom.features.storeAdmin.presentation.ProductStatusAdapter
import com.example.farmersecom.features.storeAdmin.presentation.StoreDashboardViewModel
import com.example.farmersecom.features.storeAdmin.presentation.StoreProductViewModel
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ActiveProductsFragment : BaseFragment<FragmentActiveProductsBinding>()
{

    private val  viewModel:StoreDashboardViewModel by viewModels()
    private val  productViewModel:StoreProductViewModel by viewModels()
    private lateinit var productStatusAdapter:ProductStatusAdapter
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentActiveProductsBinding
    {
        return FragmentActiveProductsBinding.inflate(inflater,container,false)
    } // onCreateView closed



    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler(binding.fragmentActiveProductsRecyclerView)

        viewModel.getProductByStatus(true)

        subscribeToActiveProductsResponse()
        subscribeToProductStatusChangedResponse()

    } // onViewCreated closed


    private fun subscribeToProductStatusChangedResponse()
    {

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                productViewModel.statusMsgResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Success ->
                        {
                            Timber.tag(Constants.TAG).d("${it.data}")
                            requireContext().showToast(it.data?.message.toString())
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscribeToActiveProductsResponse








    private fun subscribeToActiveProductsResponse()
    {

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.productByStatusResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Success ->
                        {
                            Timber.tag(Constants.TAG).d("${it.data}")
                            productStatusAdapter.submitList(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscribeToActiveProductsResponse

    private fun setupRecycler(recycler: RecyclerView)
    {
        productStatusAdapter = ProductStatusAdapter(requireContext()) {  }
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = productStatusAdapter


        productStatusAdapter.onChangeStatusClick()
        {
            status,postion,id->

            Timber.tag(Constants.TAG).d("id"+id)
            productViewModel.changeProductStatus(status,id)
            viewModel.getProductByStatus(true)
            productStatusAdapter.notifyItemRemoved(postion)

        }

    } // setupHomeSlider closed

} // activeProductsFragment closed