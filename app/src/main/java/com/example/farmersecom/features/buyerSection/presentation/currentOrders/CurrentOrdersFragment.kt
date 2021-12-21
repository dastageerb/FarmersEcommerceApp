package com.example.farmersecom.features.buyerSection.presentation.currentOrders

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
import com.example.farmersecom.databinding.FragmentCurrentOrdersBinding
import com.example.farmersecom.features.buyerSection.presentation.BuyerDashboardViewModel
import com.example.farmersecom.features.buyerSection.presentation.OrderStatusAdapter
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber



@AndroidEntryPoint
class CurrentOrdersFragment : BaseFragment<FragmentCurrentOrdersBinding>()
{

    private val viewModel:BuyerDashboardViewModel by activityViewModels()
    private lateinit var orderStaAdapter: OrderStatusAdapter

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentCurrentOrdersBinding
    {
        return FragmentCurrentOrdersBinding.inflate(inflater,container,false)
    } // onCreate closed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        setupRecycler(binding.fragmentCurrentOrdersRecyclerView)
        viewModel.getBuyerOrders(true)
        subscribeToBuyerCurrentOrdersResponseFlow()

    } // onViewCreated closed


    private fun subscribeToBuyerCurrentOrdersResponseFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.buyerOrderResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {

                        }
                        is NetworkResource.Success ->
                        {
                            Timber.tag(Constants.TAG).d("${it.data}")
                            orderStaAdapter.submitList(it.data?.orders)
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
        orderStaAdapter = OrderStatusAdapter()
        {
            viewModel.setOrderId(it)
            findNavController().navigate(R.id.action_currentOrdersFragment_to_orderDetailsForBuyerFragment)
        }
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = orderStaAdapter
    } // setupHomeSlider closed

} // CurrentOrdersFragment