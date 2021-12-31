package com.example.farmersecom.features.home.presentation.moreSliderItems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentMoreSliderItemsBinding
import com.example.farmersecom.features.home.domain.model.more.MoreProduct
import com.example.farmersecom.features.home.presentation.MoreItemsAdapter
import com.example.farmersecom.features.home.presentation.SharedViewModel
import com.example.farmersecom.features.productDetails.presentation.productDetails.ProductDetailsViewModel
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MoreSliderItemsFragment : BaseFragment<FragmentMoreSliderItemsBinding>()
{

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val productDetailsViewModel: ProductDetailsViewModel by activityViewModels()
    private lateinit var moreItemsAdapter: MoreItemsAdapter
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

                sharedViewModel.getMoreSliderItems(it)
                Timber.tag(TAG).d("$it")
            } //
        } // viewLifeCycleOwner

        setupMoreItemsRecycler(binding.moreSliderItemsFragmentRecyclerView)

        subscribeToMoreSliderResponseFlow()
    } // onViewCreated closed

    private fun subscribeToMoreSliderResponseFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                sharedViewModel.moreItemsResponseProduct.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Success ->
                        {
                            binding.moreSliderItemsFragmentProgressBar.hide()
                            Timber.tag(Constants.TAG).d("${it.data?.products}")
                            moreItemsAdapter.submitList(it.data?.products)

                            Timber.tag(Constants.TAG).d("after success -> ${moreItemsAdapter.itemCount}")
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                        is NetworkResource.Loading ->
                        {
                            binding.moreSliderItemsFragmentProgressBar.show()
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscribeToSearchResponseFlow



    private fun setupMoreItemsRecycler(recycler:RecyclerView)
    {
        moreItemsAdapter = MoreItemsAdapter()
        {
            productDetailsViewModel.setProductId(it)
            findNavController().navigate(R.id.action_moreSliderItemsFragment_to_productDetailsFragment)
        }
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = moreItemsAdapter
       // fakeList()
    } // setupHomeSlider closed

    fun fakeList()
    {
        val list = mutableListOf<MoreProduct>()
        list.add(MoreProduct("dd","dd","ff","ff","sdd",1,2,"2"))
        list.add(MoreProduct("dd","dd","ff","ff","sdd",1,2,"2"))
        list.add(MoreProduct("dd","dd","ff","ff","sdd",1,2,"2"))
        list.add(MoreProduct("dd","dd","ff","ff","sdd",1,2,"2"))
        list.add(MoreProduct("dd","dd","ff","ff","sdd",1,2,"2"))
        moreItemsAdapter.submitList(list)
    }



} // MoreSliderItemsFragment closed