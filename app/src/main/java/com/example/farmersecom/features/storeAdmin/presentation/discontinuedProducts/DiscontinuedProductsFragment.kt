package com.example.farmersecom.features.storeAdmin.presentation.discontinuedProducts

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
import com.example.farmersecom.databinding.FragmentDiscontinuedProductsBinding
import com.example.farmersecom.features.storeAdmin.presentation.ProductStatusAdapter
import com.example.farmersecom.features.storeAdmin.presentation.StoreDashboardViewModel
import com.example.farmersecom.features.storeAdmin.presentation.StoreProductViewModel
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


@InternalCoroutinesApi
@AndroidEntryPoint
class DiscontinuedProductsFragment : BaseFragment<FragmentDiscontinuedProductsBinding>()
{

    private val  viewModel: StoreDashboardViewModel by viewModels()

    private val  productViewModel: StoreProductViewModel by viewModels()
    private lateinit var productStatusAdapter: ProductStatusAdapter
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentDiscontinuedProductsBinding
    {
        return FragmentDiscontinuedProductsBinding.inflate(inflater,container,false)
    } // onCreateView closed



    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler(binding.fragmentDiscontinuedRecyclerView)
        viewModel.getProductByStatus(false)
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
            Timber.tag(TAG).d("position"+postion)
            productViewModel.changeProductStatus(status,id)
            productStatusAdapter.notifyItemRemoved(postion)
            viewModel.getProductByStatus(false)
        }

        productStatusAdapter.onDeleteProductClicked()
        {
                postion,id->

            Timber.tag(Constants.TAG).d("id : "+id)
            productViewModel.deleteProductbyId(id)
          //  productStatusAdapter.notifyItemRemoved(postion)
           // viewModel.getProductByStatus(false)

        }

    } // setupHomeSlider closed

} // activeProductsFragment closed


