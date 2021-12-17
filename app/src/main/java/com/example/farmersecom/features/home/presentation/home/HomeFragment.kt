package com.example.farmersecom.features.home.presentation.home

import android.nfc.Tag
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentHomeBinding
import com.example.farmersecom.features.home.domain.model.HomeChildProduct
import com.example.farmersecom.features.home.domain.model.HomeLatestItem
import com.example.farmersecom.features.home.domain.model.HomeSlider
import com.example.farmersecom.features.home.presentation.HomeViewModel
import com.example.farmersecom.features.home.presentation.SharedViewModel
import com.example.farmersecom.features.home.presentation.home.adapters.HomeLatestProductsAdapter
import com.example.farmersecom.features.home.presentation.home.adapters.HomeSliderAdapter
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment :BaseFragment<FragmentHomeBinding>()
{


    private val homeViewModel: HomeViewModel by viewModels()
    private val sharedViewModel:SharedViewModel by activityViewModels()
    private lateinit var homeSliderAdapter:HomeSliderAdapter
    private lateinit var latestProductsAdapter:HomeLatestProductsAdapter

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentHomeBinding
    {
        return  FragmentHomeBinding.inflate(inflater,container,false)
    } // onCreateView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getSliderItems()
        homeViewModel.getHomeLatestItems()

        setupHomeSlider(binding.homeFragmentSliderRecyclerView)
        setupLatestProductsRecycler(binding.homeFragmentLatestProductsRecyclerView)

        subscribeSliderResponse()
        subscribeLatestProductsResponse()

    } // onViewCreated closed


    private fun subscribeSliderResponse()
    {
        viewLifecycleOwner.lifecycleScope.launch()
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                homeViewModel.getSliderItemsResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            Timber.tag(Constants.TAG).d("Loading")
                        }
                        is NetworkResource.Error ->
                        {
                        //    requireContext().showToast(it.msg.toString())
                            Timber.tag(Constants.TAG).d(it.msg)
                        }
                        is NetworkResource.Success ->
                        {
                            Timber.tag(Constants.TAG).d(it.data.toString())
                            homeSliderAdapter.submitList(it.data)
                        }
                        else -> {}
                    } /// when closed
                } // collect closed
            } // repeatOnLifeCycle closed
        } // lifeCycleScope closed
    } // subscribeRegisterResponse  closed

    private fun subscribeLatestProductsResponse()
    {
        viewLifecycleOwner.lifecycleScope.launch()
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                homeViewModel.getHomeLatestResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            Timber.tag(Constants.TAG).d("Loading")
                        }
                        is NetworkResource.Error ->
                        {
                          //  requireContext().showToast(it.msg.toString())
                            Timber.tag(Constants.TAG).d(it.msg)
                            latestProductsAdapter.submitList(fakeHomeLatestList())
                        }
                        is NetworkResource.Success ->
                        {
                            Timber.tag(Constants.TAG).d(it.data.toString())
                            latestProductsAdapter.submitList(it.data)
                        }
                        else -> {}
                    } /// when closed
                } // collect closed
            } // repeatOnLifeCycle closed
        } // lifeCycleScope closed
    } // subscribeRegisterResponse  closed

    private fun setupHomeSlider(recycler:RecyclerView)
    {
        homeSliderAdapter = HomeSliderAdapter()
        {
            sharedViewModel.setSliderQuery(it)
            findNavController().navigate(R.id.action_homeFragment_to_moreSliderItemsFragment)
        } // HomeSliderAdapter
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL ,false)
        recycler.adapter = homeSliderAdapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recycler);
    } // setupHomeSlider closed

    private fun setupLatestProductsRecycler(recycler:RecyclerView)
    {

         // latestProductsAdapter and click listener
        latestProductsAdapter= HomeLatestProductsAdapter(requireContext())
        {
            sharedViewModel.setCategoryItem(it)
            findNavController().navigate(R.id.action_homeFragment_to_seeAllLatestItemsFragment)
        } // adapter and click listener closed


        // when childRecyclerView inside LatestProductsAdapter is clicked
        // get product id and go to productDetails page
        latestProductsAdapter.setOnChildClickListener()
        {
            Timber.tag(TAG).d("$it")
        } // latestProductsAdapter childClickListener closed

        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = latestProductsAdapter
    } // setupLatestProductsRecycler closed


    private fun fakeSliderList():List<HomeSlider>
    {
        val list = mutableListOf<HomeSlider>()
        list.add(HomeSlider("Potato","123"))
        list.add(HomeSlider("Tomato","123"))
        return list
    } // fakeSliderList closed


    private fun fakeChildList():List<HomeChildProduct>
    {
        val list = mutableListOf<HomeChildProduct>()
        list.add(HomeChildProduct("61b3062c658dc50016921ad0","","123"))
        list.add(HomeChildProduct("61b304a6658dc50016921ac6","","123"))
        list.add(HomeChildProduct("61b3055c658dc50016921ac8","","123"))
        return list
    } // fakeSliderList closed


    private fun fakeHomeLatestList():List<HomeLatestItem>
    {
        val list = mutableListOf<HomeLatestItem>()
        list.add(HomeLatestItem("616e6721f2a0e80d7044c395","veg",fakeChildList()))
        list.add(HomeLatestItem("1456","fruit",fakeChildList()))
        list.add(HomeLatestItem("98124","dairy",fakeChildList()))
        return list
    }

} // HomeFragment closed