package com.example.farmersecom.features.home.presentation.seeAllLatestItems

import android.os.Bundle
import androidx.fragment.app.Fragment
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
import com.example.farmersecom.databinding.FragmentSeeAllLatestItemsBinding
import com.example.farmersecom.features.home.presentation.MoreItemsAdapter
import com.example.farmersecom.features.home.presentation.SharedViewModel
import com.example.farmersecom.features.productDetails.presentation.productDetails.ProductDetailsViewModel
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class SeeAllLatestItemsFragment : BaseFragment<FragmentSeeAllLatestItemsBinding>()
{

    private lateinit var moreItemsAdapter: MoreItemsAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val productDetailsViewModel: ProductDetailsViewModel by activityViewModels()
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
                Timber.tag(Constants.TAG).d("category name = ${it.categoryName}")
                Timber.tag(Constants.TAG).d("category name = ${it.categoryId}")
                sharedViewModel.getMoreCategoryItems(it.categoryId)
            } //
        } // viewLifeCycleOwner

        setupMoreItemsRecycler(binding.seeAllLatestItemsFragmentRecyclerView)
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
                            if(it.data?.products.isNullOrEmpty())
                            {
                                requireContext().showToast(getString(R.string.no_items_found_in))
                            }

                            binding.moreSeeAllLatestFragmentProgressBar.hide()
                         //   binding.moreSliderItemsFragmentProgressBar.hide()
                            Timber.tag(Constants.TAG).d("${it.data?.products}")
                            moreItemsAdapter.submitList(it.data?.products)
                            Timber.tag(Constants.TAG).d("after success -> ${moreItemsAdapter.itemCount}")
                        }
                        is NetworkResource.Error ->
                        {

                            binding.moreSeeAllLatestFragmentProgressBar.hide()
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                        is NetworkResource.Loading ->
                        {
                            binding.moreSeeAllLatestFragmentProgressBar.show()
                           // binding.moreSliderItemsFragmentProgressBar.show()
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscribeToSearchResponseFlow



    private fun setupMoreItemsRecycler(recycler: RecyclerView)
    {
        moreItemsAdapter = MoreItemsAdapter()
        {
            productDetailsViewModel.setProductId(it)
            findNavController().navigate(R.id.action_seeAllLatestItemsFragment_to_productDetailsFragment)
        }
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = moreItemsAdapter
        // fakeList()
    } // setupHomeSlider closed


} // SeeAllLatestItemsFragment