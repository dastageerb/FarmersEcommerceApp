package com.example.farmersecom.features.productStore.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentProductStoreBinding
import com.example.farmersecom.features.home.presentation.home.adapters.HomeSliderAdapter
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
@InternalCoroutinesApi
class ProductStoreFragment : BaseFragment<FragmentProductStoreBinding>()
{

    private val id = "616fe967f92cd90016fc069";
    private val storeViewModel:ProductStoreViewModel by activityViewModels()
    private val viewModel:ProductStoreViewModel by activityViewModels()
    private lateinit var storeProductsAdapter: StoreProductsAdapter
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentProductStoreBinding
    {
        return FragmentProductStoreBinding.inflate(inflater,container,false);
    } //

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launch()
        {
            storeViewModel.getStoreId.collect()
            {
                viewModel.getStoreDetails(id)
            }
        }


        setupStoreRecycler(binding.fragmentProductStoreRecyclerView)
        //viewModel.getStoreProducts(id)
        subscribeToStoreDetailsResponseFlow()
        subscribeToStoreProductsResponseFlow()


    } // onViewCreated


    private fun subscribeToStoreDetailsResponseFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.productStoreResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Success ->
                        {
                            requireContext().showToast(it.data.toString())
                            Timber.tag(Constants.TAG).d("${it.data}")
                            // updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscribeToSearchResponseFlow


    private fun subscribeToStoreProductsResponseFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.storeProductsResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Success ->
                        {
                            Timber.tag(Constants.TAG).d("${it.data}")
                            storeProductsAdapter.submitList(it.data)
                            // updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscribeToStoreResponseFlow

    private fun setupStoreRecycler(recycler: RecyclerView)
    {
        storeProductsAdapter = StoreProductsAdapter()
   
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        recycler.adapter = storeProductsAdapter
    } // setupHomeSlider closed


} // productStoreFragment