package com.example.farmersecom.features.home.presentation.home

import android.os.Bundle
import android.os.Handler
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
import com.example.farmersecom.features.home.presentation.HomeViewModel
import com.example.farmersecom.features.home.presentation.SharedViewModel
import com.example.farmersecom.features.home.presentation.home.adapters.HomeLatestProductsAdapter
import com.example.farmersecom.features.home.presentation.home.adapters.HomeSliderAdapter
import com.example.farmersecom.features.productDetails.presentation.productDetails.ProductDetailsViewModel
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import android.os.Looper
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*


@AndroidEntryPoint
class HomeFragment :BaseFragment<FragmentHomeBinding>(),View.OnClickListener
{


    private val homeViewModel: HomeViewModel by viewModels()
    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val productDetailsViewModel: ProductDetailsViewModel by activityViewModels()
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
       // homeViewModel.getHomeLatestItems()

        setupHomeSlider(binding.homeFragmentSliderRecyclerView)
        setupLatestProductsRecycler(binding.homeFragmentLatestProductsRecyclerView)

        subscribeSliderResponse()
        subscribeLatestProductsResponse()


        binding.fragmentHomeNoInternetLayout.layoutNoInternetRetryButton.setOnClickListener(this)

        // token = "dC67tvRMTYSSlEIejD1ES_:APA91bFe8Fij5Bg46KvuV6vhkLQFjX2UBV65Ea-UfBC7jRPGVkhkqDsoE_f-NC37EXtHA6niYbDbzUnu3MyFzFNx0DjtV85nSg9ck_7Mn3AYK-kspqjDgBMEAVofQgmBJhi47CSWT-5t"



//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener
//        {
//            Timber.tag(TAG).d("fcm token = "+it.result)
//        })


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
                            setVisibilityOfView(true,false)
                            Timber.tag(Constants.TAG).d("Loading")
                        }
                        is NetworkResource.Error ->
                        {
                            setVisibilityOfView(false,true,it.msg)
                        }
                        is NetworkResource.Success ->
                        {
                            Timber.tag(TAG).d("${it.data}")
                            homeSliderAdapter.submitList(it.data)
                        }
                        else -> {}
                    } /// when closed
                } // collect closed
            } // repeatOnLifeCycle closed
        } // lifeCycleScope closed
    } // subscriberResponse  closed

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

                            setVisibilityOfView(false,true,it.msg)
                            Timber.tag(Constants.TAG).d(it.msg)
                        }
                        is NetworkResource.Success ->
                        {
                            setVisibilityOfView(false,false)
                            Timber.tag(Constants.TAG).d(it.data.toString())
                            latestProductsAdapter.submitList(it.data)
                            Timber.tag(TAG).d("${latestProductsAdapter.itemCount}")
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
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL ,false)
        recycler.layoutManager = linearLayoutManager
        recycler.adapter = homeSliderAdapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recycler);



//        val duration = 1000
//        val pixelsToMove = 300
//        val mHandler = Handler(Looper.getMainLooper())
//        val SCROLLING_RUNNABLE: Runnable = object : Runnable
//        {
//            override fun run()
//            {
//                recycler.smoothScrollBy(pixelsToMove, 0)
//                mHandler.postDelayed(this, duration.toLong())
//            }
//        }
//
//        recycler.post(SCROLLING_RUNNABLE)


    } // setupHomeSlider closed

    private fun setupLatestProductsRecycler(recycler:RecyclerView)
    {

         // latestProductsAdapter and click listener
        latestProductsAdapter= HomeLatestProductsAdapter(requireContext())
        {
            sharedViewModel.setCategoryItem(it)
            findNavController().navigate(R.id.action_homeFragment_to_seeAllLatestItemsFragment)
        } // adapter and click listener closed

        latestProductsAdapter.setOnChildClickListener()
        {
            productDetailsViewModel.setProductId(it)
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment)
            Timber.tag(TAG).d("$it")
        } // latestProductsAdapter childClickListener closed
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = latestProductsAdapter
    } // setupLatestProductsRecycler closed


    private fun setVisibilityOfView(progressbarVisibility:Boolean
                            ,noInternetVisibility:Boolean,msg:String?=null)
    {
        if(progressbarVisibility)
        {
            binding.fragmentHomeProgressBar.show()
        }else
        {
            binding.fragmentHomeProgressBar.hide()
        }

        if(noInternetVisibility)
        {
            binding.fragmentHomeNoInternetLayout.root.show()
            binding.fragmentHomeNoInternetLayout.layoutNoInternetTextView.text = msg!!
        }else
        {
            binding.fragmentHomeNoInternetLayout.root.hide()
        }
    } // setInternet

    override fun onClick(v: View?)
    {
        when(v)
        {
            binding.fragmentHomeNoInternetLayout.layoutNoInternetRetryButton ->
            {
                homeViewModel.getSliderItems()
            }
        }

    } // onClick closed


} // HomeFragment closed