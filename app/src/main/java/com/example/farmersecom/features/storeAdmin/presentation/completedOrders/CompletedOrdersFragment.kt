package com.example.farmersecom.features.storeAdmin.presentation.completedOrders

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentCompletedOrdersBinding
import com.example.farmersecom.features.buyerSection.presentation.OrderStatusAdapter
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
class CompletedOrdersFragment : BaseFragment<FragmentCompletedOrdersBinding>()
{
    private val  viewModel: StoreDashboardViewModel by activityViewModels()
    private lateinit var orderStatusAdapter: OrderStatusAdapter

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentCompletedOrdersBinding
    {
        return FragmentCompletedOrdersBinding.inflate(inflater,container,false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        setupRecycler(binding.fragmentCompletedOrdersRecyclerView)
        viewModel.getSellerOrders(false)
        subscribeToBuyerCurrentOrdersResponseFlow()

    } // onViewCreated closed


    private fun subscribeToBuyerCurrentOrdersResponseFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.sellerOrderResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {

                        }
                        is NetworkResource.Success ->
                        {
                            Timber.tag(Constants.TAG).d("${it.data}")
                            orderStatusAdapter.submitList(it.data?.orders)
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


    private fun setupRecycler(recycler: RecyclerView)
    {
        orderStatusAdapter = OrderStatusAdapter()
        {
            viewModel.setOrderId(it)
            findNavController().navigate(R.id.action_completedOrdersFragment_to_orderDetailsForSellerFragment)
        }
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = orderStatusAdapter
    } // setupHomeSlider closed





} // Completed Orders Fragment